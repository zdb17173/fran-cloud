package org.fran.cloud.mq.extend;

import org.fran.cloud.mq.aws.exceptions.SQSInitializationException;
import org.fran.cloud.mq.aws.sqs.anno.Consumer;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSConsumer;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSConsumerProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fran on 2017/9/13.
 */
@Service
public class SQSConsumerProviderImpl implements SQSConsumerProvider{

    @Resource
    ApplicationContext applicationContext;

    @Override
    public List<SQSConsumer> getConsumers() throws SQSInitializationException {

        List<SQSConsumer> res = new ArrayList<>();
        String[] consumers = applicationContext.getBeanNamesForAnnotation(Consumer.class);
        if(consumers!= null){
            for(String cs : consumers){
                Object csObj = applicationContext.getBean(cs);
                if(csObj instanceof SQSConsumer){
                    res.add((SQSConsumer)csObj);
                }else{
                    throw new SQSInitializationException("Consumer must instanceof SQSConsumer");
                }
            }
        }
        return res;
    }
}
