package org.fran.cloud.mq.aws.sns.factories;


import org.fran.cloud.mq.aws.sns.interfaces.SNSTopic;

public class SNSTopicImpl implements SNSTopic {

	String arn;
	String topicName;
	
	public void setArn(String arn) {
		this.arn = arn;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	@Override
	public String getArn() {
		return arn;
	}

	@Override
	public String getTopicName() {
		return topicName;
	}

}
