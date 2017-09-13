package org.fran.cloud.mq.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by fran on 2017/9/12.
 */
@ConfigurationProperties(prefix = AwsMqSqsProperties.AWS_MQ_SQS_PROPERTIES_PREFIX)
public class AwsMqSqsProperties {
    public final static String AWS_MQ_SQS_PROPERTIES_PREFIX = "sqs";

    String accessKey;
    String securityKey;
    int waitTimeSeconds;
    int mainPoolSize;
    int workExecutorPoolSize;
    String region;
    String[] queuename;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public int getWaitTimeSeconds() {
        return waitTimeSeconds;
    }

    public void setWaitTimeSeconds(int waitTimeSeconds) {
        this.waitTimeSeconds = waitTimeSeconds;
    }

    public int getMainPoolSize() {
        return mainPoolSize;
    }

    public void setMainPoolSize(int mainPoolSize) {
        this.mainPoolSize = mainPoolSize;
    }

    public int getWorkExecutorPoolSize() {
        return workExecutorPoolSize;
    }

    public void setWorkExecutorPoolSize(int workExecutorPoolSize) {
        this.workExecutorPoolSize = workExecutorPoolSize;
    }

    public String[] getQueuename() {
        return queuename;
    }

    public void setQueuename(String[] queuename) {
        this.queuename = queuename;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
