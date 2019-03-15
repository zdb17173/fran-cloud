package org.fran.cloud.azure.storage.demo;

import com.microsoft.azure.storage.blob.*;
import com.microsoft.azure.storage.blob.models.BlobHTTPHeaders;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.security.InvalidKeyException;

/**
 * @author fran
 * @Description
 * @Date 2019/1/2 14:06
 */
public class Blob10Test {

    public static void main(String[] args) throws InvalidKeyException, IOException {

        String accountName = "";//设置连接账户
        String accountKey = "";//设置连接key

        SharedKeyCredentials creds = new SharedKeyCredentials(
                accountName,
                accountKey);

        final BlockBlobURL blobURL = new BlockBlobURL(
                new URL("https://" + accountName + ".blob.core.chinacloudapi.cn/myimages/frantest/myimage.jpg"),
                StorageURL.createPipeline(creds, new PipelineOptions())
        );


        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("C:\\temp\\aa.jpg"));

        TransferManagerUploadToBlockBlobOptions options = new TransferManagerUploadToBlockBlobOptions(
                null,
                new BlobHTTPHeaders()
                        .withBlobContentType("image/jpg")
                        .withBlobCacheControl("max-age=3600"),
                null,
                null,
                null);

        CommonRestResponse r = TransferManager.
                uploadFileToBlockBlob(fileChannel, blobURL, 1024 * 10, options).blockingGet();


        System.out.println(r.statusCode() + " " + r.response().request().url().toString());
        //https://cgtntestdiag967.blob.core.chinacloudapi.cn/test01/1545731276(1).png
    }
}
