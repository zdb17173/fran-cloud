package org.fran.cloud.mq.aws.sqs.factories;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.fran.cloud.mq.aws.config.AbstractAwsProxyConfig;
import org.fran.cloud.mq.aws.exceptions.SQSInitializationException;
import org.fran.cloud.mq.aws.exceptions.SQSMessageReceiveException;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSConsumer;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSConsumerProvider;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSFactory;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSQueue;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class SQSFactoryImpl extends AbstractAwsProxyConfig implements SQSFactory{

	private ExecutorService executor;
	private ExecutorService workExecutor;
	private List<SQSQueue> queues;
	private String region;
	private String accessKey;
	private String securityKey;
	private AmazonSQS sqs;
	private boolean stopped = false;
	private int mainPoolSize = 10;
	private int waitTimeSeconds = 5;
	private int workExecutorPoolSize = 10;
	private SQSConsumerProvider sqsConsumerProvider;
	private boolean startConsumer = true;
	private boolean sendMsg = true;

	@PostConstruct
	public void init() throws SQSInitializationException{
		
		executor = Executors.newFixedThreadPool(mainPoolSize);
		workExecutor = Executors.newFixedThreadPool(workExecutorPoolSize);
		
		if(queues == null || queues.size() == 0)
			return;
		else{
			ClientConfiguration config = new ClientConfiguration();
			setProxy(config);
			AmazonSQSClientBuilder builder = AmazonSQSClient.builder();
			builder.setClientConfiguration(config);
			builder.setCredentials(new AWSCredentialsProvider() {
				@Override
				public void refresh() {
					System.out.println("refresh");
				}
				@Override
				public AWSCredentials getCredentials() {
					return new AWSCredentials() {
						@Override
						public String getAWSSecretKey() {
							return securityKey;
						}
						@Override
						public String getAWSAccessKeyId() {
							return accessKey;
						}
					};
				}
			});
			builder.setRegion(region);
			sqs = builder.build();
			ListQueuesResult remoteQueues = sqs.listQueues();
			if(remoteQueues == null || remoteQueues.getQueueUrls() == null || remoteQueues.getQueueUrls().size() == 0)
				throw new SQSInitializationException("remoteQueues null");
			else{
				List<String> remoteQueueList = remoteQueues.getQueueUrls();
				for(SQSQueue queue : queues){
					if(queue.getQueueName() == null || queue.getQueueName().equals(""))
						throw new SQSInitializationException("queueName invalid["+ queue.getQueueName() +"]");

					remoteQueueList.stream().filter(queueUrl -> queueUrl != null && queueUrl.endsWith("/" + queue.getQueueName())).forEach(queue::setQueueUrl);
					
					if(queue.getQueueUrl() == null)
						throw new SQSInitializationException("queueUrl null["+ queue.getQueueName() +"]");
				}
			}

			if(startConsumer){
				if(sqsConsumerProvider.getConsumers()!= null && sqsConsumerProvider.getConsumers().size()> 0)
					for(SQSConsumer cs : sqsConsumerProvider.getConsumers()){
						registerMessageConsumer(cs);
					}
			}

		}
	}
	
	private void registerMessageConsumer(SQSConsumer cs) throws SQSInitializationException{
		if(cs == null || cs.getQueue() == null || getQueue(cs.getQueue()) == null || getQueue(cs.getQueue()).getQueueUrl() == null)
			throw new SQSInitializationException("registerMessageConsumerError");
		SQSQueue queue = getQueue(cs.getQueue());
		
		executor.execute(() -> {
			while(true){
				if(Thread.interrupted() || stopped){
					break;
				}else{
					try{
						ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queue.getQueueUrl());
						receiveMessageRequest.setWaitTimeSeconds(waitTimeSeconds);
						ReceiveMessageResult messages = sqs.receiveMessage(receiveMessageRequest);
						if(messages != null && messages.getMessages()!= null && messages.getMessages().size() >0){

							for(final Message msg : messages.getMessages()){
								workExecutor.execute(() -> {
                                    try {
                                        String message = SQSMessageBody.decode(msg.getBody());
                                        cs.handle(message);
                                        String messageReceiptHandle = msg.getReceiptHandle();
                                        sqs.deleteMessage(new DeleteMessageRequest()
                                                .withQueueUrl(queue.getQueueUrl())
                                                .withReceiptHandle(messageReceiptHandle));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
							}
						}
					} catch (Exception e) {
						new SQSMessageReceiveException(queue.getQueueUrl(), e).printStackTrace();
					}
				}
			}
		});
		System.out.println("registerMessageConsumer["+ queue.getQueueName() +"]");
	}

	public SQSQueue getQueue(String name){
		if(name!= null){
			for(SQSQueue q : queues)
				if(q.getQueueName()!= null && q.getQueueName().equals(name))
					return q;
		}
		return null;
	}
	
	public SQSClient getClient(SQSQueue queue){
		if(queue == null || queue.getQueueName() == null || "".equals(queue.getQueueName()))
			return null;
		return new SQSClient(sqs, queue, sendMsg);
	}

	public SQSClient getClient(String qName){
		SQSQueue queue = getQueue(qName);
		if(queue == null || queue.getQueueName() == null || "".equals(queue.getQueueName()))
			return null;
		else
			return new SQSClient(sqs, queue, sendMsg);
	}
	
	@PreDestroy
	public void destroy(){
		if(stopped)
			return;

		stopped = true;
		if(workExecutor != null)
			workExecutor.shutdown();
		if(executor != null)
			executor.shutdown();
		System.out.println("destroy");
	}

	public List<SQSQueue> getQueues() {
		return queues;
	}

	public void setQueues(List<SQSQueue> queues) {
		this.queues = queues;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public void setMainPoolSize(int mainPoolSize) {
		this.mainPoolSize = mainPoolSize;
	}

	public void setWaitTimeSeconds(int waitTimeSeconds) {
		this.waitTimeSeconds = waitTimeSeconds;
	}

	public void setWorkExecutorPoolSize(int workExecutorPoolSize) {
		this.workExecutorPoolSize = workExecutorPoolSize;
	}

	public void setSqsConsumerProvider(SQSConsumerProvider sqsConsumerProvider) {
		this.sqsConsumerProvider = sqsConsumerProvider;
	}
	public void setStartConsumer(boolean startConsumer) {
		this.startConsumer = startConsumer;
	}
	public void setSendMsg(boolean sendMsg) {
		this.sendMsg = sendMsg;
	}
}
