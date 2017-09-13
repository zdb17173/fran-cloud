package org.fran.cloud.mq.aws.sns.interfaces;

import org.fran.cloud.mq.aws.sns.factories.SNSClient;

import java.util.List;


public interface SNSFactory {
	public SNSClient getClient(SNSTopic topic);
	public List<SNSTopic> getTopics();
	public SNSClient getClient(String topic);
	public SNSTopic getTopic(String name);
}
