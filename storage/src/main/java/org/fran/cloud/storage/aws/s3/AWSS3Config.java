package org.fran.cloud.storage.aws.s3;

/**
 * Created by fran on 2017/10/13.
 */
public class AWSS3Config {

    private String region;
    private String bucket;
    private String credentials;
    private String accessKeySecret;

    public AWSS3Config(){}

    public AWSS3Config(String region, String bucket, String credentials, String accessKeySecret) {
        this.region = region;
        this.bucket = bucket;
        this.credentials = credentials;
        this.accessKeySecret = accessKeySecret;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
