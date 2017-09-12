package org.fran.cloud.mq.aws.sqs.factories;

import org.fran.cloud.mq.aws.sqs.interfaces.SQSQueue;

public class SQSQueueImpl implements SQSQueue{
	String queueName;
	String queueUrl;
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getQueueUrl() {
		return queueUrl;
	}
	public void setQueueUrl(String queueUrl) {
		this.queueUrl = queueUrl;
	}
	
	
}
