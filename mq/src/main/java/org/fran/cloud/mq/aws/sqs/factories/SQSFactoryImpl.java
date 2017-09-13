package org.fran.cloud.mq.aws.sqs.factories;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.fran.cloud.mq.aws.exceptions.SQSInitializationException;
import org.fran.cloud.mq.aws.exceptions.SQSMessageReceiveException;
import org.fran.cloud.mq.aws.sqs.anno.Consumer;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSConsumer;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSConsumerProvider;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSFactory;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSQueue;
import org.springframework.context.ApplicationContext;

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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class SQSFactoryImpl implements SQSFactory{

	private ExecutorService executor;
	private ExecutorService workExecutor;
	private List<SQSQueue> queues;
	private String region;
	private String accessKey;
	private String securityKey;
	private AmazonSQS sqs;
	private boolean stoped = false;
	private int mainPoolSize = 10;
	private int waitTimeSeconds = 5;
	private int workExecutorPoolSize = 10;
	private SQSConsumerProvider sqsConsumerProvider;
	
	@PostConstruct
	public void init() throws SQSInitializationException{
		
		executor = Executors.newFixedThreadPool(mainPoolSize);
		workExecutor = Executors.newFixedThreadPool(workExecutorPoolSize);
		
		if(queues == null || queues.size() == 0)
			return;
		else{
			ClientConfiguration config = new ClientConfiguration();
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
					
					for (String queueUrl : remoteQueueList) {
						if(queueUrl!= null && queueUrl.endsWith("/"+ queue.getQueueName())){
							queue.setQueueUrl(queueUrl);
						}
					}
					
					if(queue.getQueueUrl() == null)
						throw new SQSInitializationException("queueUrl null["+ queue.getQueueName() +"]");
				}
			}

			if(sqsConsumerProvider.getConsumers()!= null && sqsConsumerProvider.getConsumers().size()> 0)
				for(SQSConsumer cs : sqsConsumerProvider.getConsumers()){
					registerMessageConsumer(cs);
				}
		}
	}
	
	private void registerMessageConsumer(SQSConsumer cs) throws SQSInitializationException{
		if(cs == null || cs.getQueue() == null)
			throw new SQSInitializationException("registMessageConsumerError");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {  
		    private static final long serialVersionUID = 1L;
		    @Override  
		    public String nameForSetterMethod(MapperConfig<?> config,  
		            AnnotatedMethod method, String defaultName) {  
		        return method.getName().substring(3);  
		    }
		    @Override  
		    public String nameForGetterMethod(MapperConfig<?> config,  
		            AnnotatedMethod method, String defaultName) {  
		        return method.getName().substring(3);  
		    }  
		});
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		executor.execute(new Runnable() {
			@Override
			public void run() {
				while(true){
					if(Thread.interrupted() || stoped){
						break;
					}else{
						try{
							ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(getQueue(cs.getQueue()).getQueueUrl());
							receiveMessageRequest.setWaitTimeSeconds(waitTimeSeconds);
							ReceiveMessageResult message = sqs.receiveMessage(receiveMessageRequest);
							if(message != null && message.getMessages()!= null && message.getMessages().size() >0){
								
								for(final Message msg : message.getMessages()){
									workExecutor.execute(new Runnable() {
										@Override
										public void run() {
											try {
												String message = SQSMessageBody.decode(msg.getBody());												
												cs.handle(message);
												String messageReceiptHandle = msg.getReceiptHandle();
												sqs.deleteMessage(new DeleteMessageRequest()
														.withQueueUrl(getQueue(cs.getQueue()).getQueueUrl())
														.withReceiptHandle(messageReceiptHandle));
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									});
								}
							}
						}catch (Exception e) {
							new SQSMessageReceiveException(getQueue(cs.getQueue()).getQueueUrl(), e).printStackTrace();
						}
						
					}
				}
			}
		});
		System.out.println("registerMessageConsumer["+ getQueue(cs.getQueue()).getQueueName() +"]");
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
		return new SQSClient(sqs, queue);
	}
	
	@PreDestroy
	public void destory(){
		stoped = true;
		if(workExecutor != null)
			workExecutor.shutdown();
		if(executor != null)
			executor.shutdown();
		System.out.println("destory");
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
}
