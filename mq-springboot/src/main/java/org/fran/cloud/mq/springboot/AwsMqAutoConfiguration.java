package org.fran.cloud.mq.springboot;

import org.fran.cloud.mq.aws.exceptions.SQSInitializationException;
import org.fran.cloud.mq.aws.sns.factories.SNSFactoryImpl;
import org.fran.cloud.mq.aws.sns.factories.SNSTopicImpl;
import org.fran.cloud.mq.aws.sns.interfaces.SNSFactory;
import org.fran.cloud.mq.aws.sns.interfaces.SNSTopic;
import org.fran.cloud.mq.aws.sqs.anno.Consumer;
import org.fran.cloud.mq.aws.sqs.factories.SQSFactoryImpl;
import org.fran.cloud.mq.aws.sqs.factories.SQSQueueImpl;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSConsumer;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSFactory;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSQueue;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fran on 2017/9/12.
 */
@Configuration
@EnableConfigurationProperties({AwsMqSnsProperties.class, AwsMqSqsProperties.class})
public class AwsMqAutoConfiguration{

    private AwsMqSnsProperties snsProperties;
    private AwsMqSqsProperties sqsProperties;
    private SQSFactoryImpl sqsFactory;
    private SNSFactoryImpl snsFactory;

    @Resource
    private ApplicationContext applicationContext;

    public AwsMqAutoConfiguration(AwsMqSnsProperties snsProperties,
                                  AwsMqSqsProperties sqsProperties){
        this.snsProperties = snsProperties;
        this.sqsProperties = sqsProperties;
    }

    @PostConstruct
    public void init() throws Exception{
        if(!StringUtils.isEmpty(snsProperties.getAccessKey())
                && !StringUtils.isEmpty(snsProperties.getSecurityKey())){
            List<SNSTopic> tArray = new ArrayList<>();
            for(String topic : snsProperties.getTopicname()){
                SNSTopicImpl tImpl = new SNSTopicImpl();
                tImpl.setTopicName(topic);
                tArray.add(tImpl);
            }

            snsFactory = new SNSFactoryImpl();
            snsFactory.setAccessKey(snsProperties.getAccessKey());
            snsFactory.setSecurityKey(snsProperties.getSecurityKey());
            snsFactory.setRegion(snsProperties.getRegion());
            snsFactory.setTopics(tArray);
            snsFactory.init();
        }
        if(!StringUtils.isEmpty(sqsProperties.getAccessKey())
                && !StringUtils.isEmpty(sqsProperties.getSecurityKey())
                && sqsProperties.getQueuename()!= null
                && sqsProperties.getQueuename().length > 0
                ){

            List<SQSQueue> qArray = new ArrayList<>();
            for(String qName : sqsProperties.getQueuename()){
                SQSQueueImpl queue = new SQSQueueImpl();
                queue.setQueueName(qName);
                qArray.add(queue);
            }

            sqsFactory = new SQSFactoryImpl();
            sqsFactory.setAccessKey(sqsProperties.getAccessKey());
            sqsFactory.setSecurityKey(sqsProperties.getSecurityKey());
            sqsFactory.setQueues(qArray);
            sqsFactory.setRegion(sqsProperties.getRegion());
            sqsFactory.setSqsConsumerProvider(() -> {
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
            });
            sqsFactory.init();
        }
        System.out.println("init");
    }

    @PreDestroy
    public void destroy(){
        sqsFactory.destory();
    }

    @Bean
    public SQSFactory sqsFactory(){
        return sqsFactory;
    }
    @Bean
    public SNSFactory snsFactory() { return snsFactory; }
}
