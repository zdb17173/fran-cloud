

# USAGE


SNS与SQS的Application.yml配置如下

测试代码见/src/test/java/org/fran/cloud/md/test/

```
sns:
  accessKey: accessKey
  securityKey: securityKey
  region: cn-north-1
  topicname:
    - topicName
sqs:
  accessKey: accessKey
  securityKey: securityKey
  region: cn-north-1
  waitTimeSeconds: 5
  mainPoolSize: 10
  workExecutorPoolSize: 10
  queuename:
    - queue1
    - queue2

```



