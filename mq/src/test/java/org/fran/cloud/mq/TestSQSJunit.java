package org.fran.cloud.mq;

import javax.annotation.Resource;


import org.fran.cloud.mq.aws.sns.factories.SNSClient;
import org.fran.cloud.mq.aws.sns.interfaces.SNSFactory;
import org.fran.cloud.mq.aws.sns.interfaces.SNSTopic;
import org.fran.cloud.mq.aws.sqs.factories.SQSClient;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSFactory;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

@ContextConfiguration(locations = "classpath*:application-test-sqs.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSQSJunit {

	@Resource
	SNSFactory sNSFactory;
	@Resource
	SQSFactory sQSFactory;
	@Resource
	SQSQueue testQueue;
	@Resource
	SNSTopic testTopic;
	
	@Test
	public void testTopic(){
		SNSClient cli = sNSFactory.getClient(testTopic);

		for(int i = 0 ; i < 1000; i ++)
		cli.sendMessage("111");
		
		try {
			Thread.sleep(1000000l);

			System.out.println("end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	@Test
	public void testQueue(){
		
		/*Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				SQSClient cli = sQSFactory.getClient(queue1);
				for(int i =0; i < 10; i ++)
					cli.send(i + "ddsadjsakd");
			}
		});
		t.start();*/
		SQSClient cli = sQSFactory.getClient(testQueue);
		try {
			cli.send("ddsadjsakd");
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("dsa");
		try {
			Thread.sleep(1000000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
