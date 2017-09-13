# fran-cloud



# fran-cloud/mq

亚马逊消息收发服务SQS及SNS的简单封装，与spring结合紧密

sqs官网地址：[sqs](https://aws.amazon.com/cn/sqs/?nc2=h_m1 "sqs")

sns官网地址：[sns](https://aws.amazon.com/cn/sns/?nc2=h_m1 "sns")


# SQS


配置：
```xml
<bean id="newsPublishQueue" class="org.fran.cloud.mq.aws.sqs.factories.SQSQueueImpl">
    <property name="queueName" value="#{config['q.sqs.queue.translate']}" />
</bean>

<bean id="csProvider" class="org.fran.cloud.mq.extend.SQSConsumerProviderImpl"></bean>

<bean id="sQSFactory" class="org.fran.cloud.mq.aws.sqs.factories.SQSFactoryImpl">
	<property name="region" value="#{config['q.sqs.region']}" />
	<property name="accessKey" value="#{config['q.sqs.accesskey']}" />
	<property name="securityKey" value="#{config['q.sqs.securityKey']}" />
	<property name="waitTimeSeconds" value="5" />
	<property name="mainPoolSize" value="10" />
	<property name="workExecutorPoolSize" value="10" />
	<property name="queues">
		<list>
			<ref bean="newsPublishQueue" />
			<ref bean="layoutQueue" />
		</list>
	</property>
</bean>

```


生产者代码：

```java

@Resource
SQSFactory sQSFactory;
@Resource
SQSQueue newsPublishQueue;

public void test(){
    SQSClient cli = sQSFactory.getClient(queue1);
    cli.send("message");
}


```

消费者代码：
```java

@Service
@Consumer
public class Consumer1 implements SQSConsumer {

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
		return "queueName";
	}

}

```


# SNS

亚马逊SNS通过控制台将消息分发到多个endpoint上，例如http、https、sqs、emal等，因此只有消息发送，消息接收通过SQS消费者获取。

配置：
```xml
<bean id="newsPublishTopic" class="com.cgtn.convergence.q.sns.factories.SNSTopicImpl">
    <property name="topicName" value="#{config['q.sns.topic.translate']}" />
</bean>

<bean id="snsFactory" class="com.cgtn.convergence.q.sns.factories.SNSFactoryImpl">
	<property name="region" value="#{config['q.sns.region']}" />
	<property name="accessKey" value="#{config['q.sns.accesskey']}" />
	<property name="securityKey" value="#{config['q.sns.securityKey']}" />
	<property name="topics">
		<list>
			<ref bean="newsPublishTopic" />
		</list>
	</property>
</bean>
```

生产者：

```java
@Resource
SNSFactory sNSFactory;
@Resource
SNSTopic newsPublishTopic;

SNSClient cli = sNSFactory.getClient(newsPublishTopic);
cli.sendMessage("111");

```

