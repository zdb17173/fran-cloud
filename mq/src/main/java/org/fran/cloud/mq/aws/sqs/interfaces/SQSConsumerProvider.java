package org.fran.cloud.mq.aws.sqs.interfaces;

import org.fran.cloud.mq.aws.exceptions.SQSInitializationException;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSConsumer;

import java.util.List;

/**
 * Created by fran on 2017/9/13.
 */
public interface SQSConsumerProvider {
    public List<SQSConsumer> getConsumers() throws SQSInitializationException;
}
