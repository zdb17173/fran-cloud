package org.fran.cloud.mq.consumer;

import org.fran.cloud.mq.aws.sqs.anno.Consumer;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSConsumer;
import org.fran.cloud.mq.aws.sqs.interfaces.SQSQueue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Consumer
public class Consumer1 implements SQSConsumer {
	
	@Resource
	SQSQueue newsPublishQueue;
	
	int count;
	
	@Override
	public void handle(String message) throws Exception {
		synchronized (this) {
			count ++;
			if(count % 5== 0)
				throw new RuntimeException();
		}
		System.out.println(new Date() + ":["+ message +"]");
	}

	@Override
	public String getQueue() {
		return "newspublish-translate";
	}

}
