package org.fran.cloud.storage.aws.s3;

import software.amazon.awssdk.auth.AwsCredentials;
import software.amazon.awssdk.client.builder.ClientAsyncHttpConfiguration;
import software.amazon.awssdk.config.ClientOverrideConfiguration;
import software.amazon.awssdk.http.AbortableInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AdvancedConfiguration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.sync.RequestBody;
import software.amazon.awssdk.sync.StreamingResponseHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by fran on 2017/10/13.
 */
public class AWSS3ObjectStorage {

    AWSS3Config config;
    S3Client client;

    public void init(){
        client = getAwsClient(config);
    }

    private S3Client getAwsClient(AWSS3Config config){

        String accessKey = config.getAccessKeySecret();
        String credentials = config.getCredentials();
        Region region = Region.of(config.getRegion());

        S3AdvancedConfiguration adConf = S3AdvancedConfiguration.builder()
                .build();
        ClientAsyncHttpConfiguration asyncHttpConf = ClientAsyncHttpConfiguration.builder()
                .build();
        ClientOverrideConfiguration overrideConf = ClientOverrideConfiguration.builder()
                .build();

        S3Client syncClient = S3Client.builder()
                .region(region)
                .credentialsProvider(() -> new AwsCredentials(accessKey, credentials))
                .advancedConfiguration(adConf)
                .overrideConfiguration(overrideConf)
                .build();

        /*S3AsyncClient asyncClient = S3AsyncClient.builder()
                .region(region)
                .credentialsProvider(() -> new AwsCredentials(accessKey, credentials))
                .advancedConfiguration(adConf)
                .asyncHttpConfiguration(asyncHttpConf)
                .overrideConfiguration(overrideConf)
                .build();*/
        return syncClient;
    }

    public CopyObjectResponse copy(String targetBucket, String targetKey, String sourceBucket, String sourceKey){

        CopyObjectRequest cpRequest = CopyObjectRequest.builder()
                .bucket(targetBucket)
                .key(targetKey)
                .acl(ObjectCannedACL.PublicRead)
                .copySource(sourceBucket + "/" + sourceKey)
                .build();
        CopyObjectResponse res = client.copyObject(cpRequest);

        return res;
    }

    public void saveAndUpdate(String key, RequestBody requestBody){

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .key(key)
                .bucket(config.getBucket())
                .acl(ObjectCannedACL.PublicRead)
                .contentType(MediaType.probeMediaType(key))
                .contentLength(requestBody.getContentLength())
                .build();


        client.putObject(putObjectRequest, requestBody);
    }


    public void getObject(String key, OutputStream out){

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(config.getBucket())
                        .key(key)
                        .build();

        client.getObject(getObjectRequest, new StreamingResponseHandler<GetObjectResponse, Void>() {
            @Override
            public Void apply(GetObjectResponse response, AbortableInputStream inputStream) throws Exception {
                byte[] b = new byte[1024];
                String content = "";
                while (inputStream.read(b)!= -1){
                    out.write(b);
                }
                return null;
            }
        });
    }

    public void delete(String key){
        DeleteObjectRequest req = DeleteObjectRequest.builder()
                .key(key)
                .bucket(config.getBucket())
                .build();
        client.deleteObject(req);
    }

    public ListObjectsResponse list(String prefix, String marker, String delimiter){


        ListObjectsRequest request = ListObjectsRequest.builder()
                .prefix(prefix)
                .bucket(config.getBucket())
                .marker(marker)
                .delimiter(delimiter)
                .maxKeys(100)
                .build();
        ListObjectsResponse res = client.listObjects(request);
        return res;
    }


    public void setConfig(AWSS3Config config) {
        this.config = config;
    }
}
