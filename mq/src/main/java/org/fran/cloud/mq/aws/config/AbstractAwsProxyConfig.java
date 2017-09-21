package org.fran.cloud.mq.aws.config;

import com.amazonaws.ClientConfiguration;

/**
 * Created by fran on 2017/9/21.
 */
public abstract class AbstractAwsProxyConfig {
    private boolean startProxy = false;
    private String proxyHost;
    private String proxyDomain;
    private String proxyPort;

    public void setProxy(ClientConfiguration config){
        if(!startProxy)
            return;
        else{
            if(proxyPort!= null && !"".equals(proxyPort))
                config.setProxyPort(Integer.valueOf(proxyPort));
            if(proxyDomain!= null && !"".equals(proxyDomain))
                config.setProxyDomain(proxyDomain);
            if(proxyHost!= null && !"".equals(proxyHost))
                config.setProxyHost(proxyHost);
        }
    }

    public void setStartProxy(boolean startProxy) {
        this.startProxy = startProxy;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyDomain(String proxyDomain) {
        this.proxyDomain = proxyDomain;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }
}
