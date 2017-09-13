package org.fran.cloud.mq.aws.sqs.interfaces;

public interface SQSConsumer {
	
	public void handle(String message) throws Exception;

	public String getQueue();
}
