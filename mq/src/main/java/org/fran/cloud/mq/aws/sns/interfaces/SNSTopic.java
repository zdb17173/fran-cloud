package org.fran.cloud.mq.aws.sns.interfaces;

public interface SNSTopic {
	String getArn();
	String getTopicName();
	void setArn(String arn);
}
