package org.fran.cloud.mq.aws.sns.factories;


import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.fran.cloud.mq.aws.sns.interfaces.SNSTopic;

public class SNSClient {
	AmazonSNS sns;
	SNSTopic topic;
	protected SNSClient(AmazonSNS sns, SNSTopic topic){
		this.sns = sns;
		this.topic = topic;
	}
	
	public void sendMessage(String message){
		sns.publish(new PublishRequest()
				.withTopicArn(topic.getArn())
				.withMessage(message)
				);
	}
}
