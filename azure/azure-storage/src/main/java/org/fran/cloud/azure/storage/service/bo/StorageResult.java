package org.fran.cloud.azure.storage.service.bo;

/**
 * @author fran
 * @Description
 * @Date 2019/1/9 15:18
 */
public class StorageResult {
    int status;
    String description;
    String url;
    String originalUrl;

    public StorageResult(){}
    public StorageResult(int status, String description){
        this.status = status;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
