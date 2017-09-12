package org.fran.cloud.mq.aws.sqs.interfaces;

public interface SQSQueue {
	public String getQueueName();
	public String getQueueUrl();
	public void setQueueUrl(String url);
}
