package org.fran.cloud.mq.aws.sns.factories;


import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.fran.cloud.mq.aws.sns.interfaces.SNSTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SNSClient {

	private static Logger logger = LoggerFactory.getLogger(SNSClient.class);

	AmazonSNS sns;
	SNSTopic topic;
	private boolean sendMsg;

	protected SNSClient(AmazonSNS sns, SNSTopic topic, boolean sendMsg){
		this.sns = sns;
		this.topic = topic;
		this.sendMsg = sendMsg;
	}
	
	public void sendMessage(String message){
		if(sendMsg){
			sns.publish(new PublishRequest()
					.withTopicArn(topic.getArn())
					.withMessage(message)
			);
		}else{
			logger.info("SNS SendMsgFalse: Msg[" +  message + "]");
		}

	}
}
