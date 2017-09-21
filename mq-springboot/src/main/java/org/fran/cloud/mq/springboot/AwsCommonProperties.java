package org.fran.cloud.mq.springboot;

/**
 * Created by fran on 2017/9/21.
 */
public class AwsCommonProperties {
    String proxy_host;
    String proxy_port;
    boolean proxy_open;

    public String getProxy_host() {
        return proxy_host;
    }

    public void setProxy_host(String proxy_host) {
        this.proxy_host = proxy_host;
    }

    public String getProxy_port() {
        return proxy_port;
    }

    public void setProxy_port(String proxy_port) {
        this.proxy_port = proxy_port;
    }

    public boolean isProxy_open() {
        return proxy_open;
    }

    public void setProxy_open(boolean proxy_open) {
        this.proxy_open = proxy_open;
    }
}
