package org.fran.cloud.mq.aws.sqs.factories;

import java.util.HashMap;


import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSQueue;

public class SQSClient {

	private AmazonSQS sqs;
	private SQSQueue queue;

	protected SQSClient(AmazonSQS sqs, SQSQueue queue) {
		this.sqs = sqs;
		this.queue = queue;
	}

	public void send(String message) throws JsonProcessingException {
		String body = SQSMessageBody.encode(message);
		SendMessageResult res = sqs.sendMessage(
				new SendMessageRequest()
				.withMessageBody(body)
				.withQueueUrl(queue.getQueueUrl()));
	}
}
