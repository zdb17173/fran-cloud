package org.fran.cloud.azure.storage.service.config;

/**
 * @author fran
 * @Description
 * @Date 2019/1/2 10:42
 */
public class BlobConfig {
    String containerName;
    String storageConnectionString;
    String host;
    String cloudHost;

    public String getHost() {
        if(host == null)
            return "";
        else
            return host.endsWith("/")? host: host+ "/";
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCloudHost() {
        if(cloudHost == null)
            return "";
        else
            return cloudHost.endsWith("/")? cloudHost: cloudHost+ "/";
    }

    public void setCloudHost(String cloudHost) {
        this.cloudHost = cloudHost;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getStorageConnectionString() {
        return storageConnectionString;
    }

    public void setStorageConnectionString(String storageConnectionString) {
        this.storageConnectionString = storageConnectionString;
    }
}
