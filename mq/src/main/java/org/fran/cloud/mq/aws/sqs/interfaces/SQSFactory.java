package org.fran.cloud.mq.aws.sqs.interfaces;

import java.util.List;

import org.fran.cloud.mq.aws.sqs.factories.SQSClient;

public interface SQSFactory {
	public SQSClient getClient(String queue);
	public SQSClient getClient(SQSQueue queue);
	public List<SQSQueue> getQueues();
	public SQSQueue getQueue(String name);
}
