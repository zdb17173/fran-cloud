package org.fran.cloud.mq.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.fran.cloud.mq.aws.sns.factories.SNSClient;
import org.fran.cloud.mq.aws.sns.interfaces.SNSFactory;
import org.fran.cloud.mq.aws.sqs.factories.SQSClient;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/mq")
public class SendMessage {

    @Resource
    SQSFactory sqsFactory;
    @Resource
    SNSFactory snsFactory;

    //http://127.0.0.1:8080/mq/sqs/send?q=newspublish-layout&msg=111
    @GetMapping(value = "/sqs/send")
    public void sendSqs(
            @RequestParam("msg")String msg,
            @RequestParam("q")String queue){
        SQSClient cli = sqsFactory.getClient(queue);
        try {
            cli.send(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    //http://127.0.0.1:8080/mq/sns/send?q=news-publish&msg=111
    @GetMapping(value = "/sns/send")
    public void sendSns(
            @RequestParam("msg")String msg,
            @RequestParam("q")String queue){
        SNSClient cli = snsFactory.getClient(queue);
        cli.sendMessage(msg);
    }
}