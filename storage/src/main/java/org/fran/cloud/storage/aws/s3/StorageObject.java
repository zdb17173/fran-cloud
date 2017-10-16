package org.fran.cloud.storage.aws.s3;

import java.io.InputStream;
import java.util.Date;

/**
 * Created by fran on 2017/10/13.
 */
public class StorageObject {
    String key;
    String eTag;
    String content;
    Date lastModifiedTimestamp;

    public StorageObject(){}

    public StorageObject(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    public void setLastModifiedTimestamp(Date lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }
}
