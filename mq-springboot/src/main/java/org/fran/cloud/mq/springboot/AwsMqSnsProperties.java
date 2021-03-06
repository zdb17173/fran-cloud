package org.fran.cloud.mq.springboot;

/**
 * Created by fran on 2017/9/12.
 */

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = AwsMqSnsProperties.AWS_MQ_SNS_PROPERTIES_PREFIX)
public class AwsMqSnsProperties extends AwsCommonProperties {
    public final static String AWS_MQ_SNS_PROPERTIES_PREFIX = "sns";

    String accessKey;
    String securityKey;
    String region;
    String[] topicname;
    boolean sendMsg = true;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String[] getTopicname() {
        return topicname;
    }

    public void setTopicname(String[] topicname) {
        this.topicname = topicname;
    }

    public boolean isSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(boolean sendMsg) {
        this.sendMsg = sendMsg;
    }
}
