package org.fran.cloud.azure.storage.service.impl;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageUri;
import com.microsoft.azure.storage.blob.*;
import org.fran.cloud.azure.storage.service.StorageService;
import org.fran.cloud.azure.storage.service.bo.StorageResult;
import org.fran.cloud.azure.storage.service.common.MediaType;
import org.fran.cloud.azure.storage.service.config.BlobConfig;

import java.io.*;

/**
 * @author fran
 * @Description
 * @Date 2019/1/2 10:51
 */
public class StorageServiceBlobImpl implements StorageService {
    BlobConfig blobConfig;

    CloudBlobContainer container;

    public BlobConfig getConfig(){
        return blobConfig;
    }

    public StorageService init(BlobConfig blobConfig) throws Exception {
        this.blobConfig = blobConfig;
        CloudStorageAccount account = CloudStorageAccount.parse(blobConfig.getStorageConnectionString());
        CloudBlobClient serviceClient = account.createCloudBlobClient();
        container = serviceClient.getContainerReference(blobConfig.getContainerName());
        if(!container.exists())
            container.create();
        return this;
    }

    public StorageResult delete(String key){
        StorageResult res = new StorageResult();
        try {
            CloudBlockBlob blob = container.getBlockBlobReference(key);
            blob.delete();

            res.setStatus(200);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            return res;
        }
    }

    public StorageResult copy(String source, String target){
        StorageResult res = new StorageResult();
        try {
            CloudBlockBlob sourceBlob = container.getBlockBlobReference(source);
            CloudBlockBlob targetBlob = container.getBlockBlobReference(target);
            targetBlob.startCopy(sourceBlob);
            res.setStatus(200);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            return res;
        }
    }

    public void uploadFolder(String key, File folder){

        String slash = key.endsWith("/")? "" : "/";

        for(File f : folder.listFiles()){


            String k = key+ slash + folder.getName() + "/" + f.getName();
            upload(k, f);
        }
    }

    @Override
    public StorageResult upload(String key, String text) {
        StorageResult res = new StorageResult();
        CloudBlockBlob blob = null;
        try {
            blob = container.getBlockBlobReference(key);
            blob.uploadText(text);
            BlobProperties properties = blob.getProperties();
            properties.setContentType(MediaType.probeMediaType(key));
//            properties.setCacheControl("max-age=43200");
            blob.uploadProperties();

            res.setStatus(200);
            res.setUrl(blobConfig.getHost() + blobConfig.getContainerName() + "/" + key);
            res.setOriginalUrl(blobConfig.getCloudHost() + blobConfig.getContainerName() + "/" + key);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            res.setDescription(e.getMessage());
            return res;
        }

    }

    public StorageResult upload(String key, long len, InputStream inputStream){
        StorageResult res = new StorageResult();
        try {
            CloudBlockBlob blob = container.getBlockBlobReference(key);
            blob.upload(
                    inputStream,
                    len);
            BlobProperties properties = blob.getProperties();
            properties.setContentType(MediaType.probeMediaType(key));
            properties.setCacheControl("max-age=43200");
            blob.uploadProperties();

            res.setStatus(200);
            res.setUrl(blobConfig.getHost() + blobConfig.getContainerName() + "/" + key);
            res.setOriginalUrl(blobConfig.getCloudHost() + blobConfig.getContainerName() + "/" + key);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            res.setDescription(e.getMessage());
            return res;
        } finally {
            if(inputStream!= null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public StorageResult upload(String key, File sourceFile){
        StorageResult res = new StorageResult();
        try(FileInputStream fileInputStream = new FileInputStream(sourceFile)) {
            CloudBlockBlob blob = container.getBlockBlobReference(key);
            blob.upload(
                    fileInputStream,
                    sourceFile.length());

            BlobProperties properties = blob.getProperties();
            properties.setContentType(MediaType.probeMediaType(key));
            properties.setCacheControl("max-age=43200");
            blob.uploadProperties();
            res.setStatus(200);
            res.setUrl(blobConfig.getHost() + blobConfig.getContainerName() + "/" + key);
            res.setOriginalUrl(blobConfig.getCloudHost() + blobConfig.getContainerName() + "/" + key);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            res.setDescription(e.getMessage());
            return res;
        }
    }

    public StorageResult download(String key, File target){
        StorageResult res = new StorageResult();
        try (FileOutputStream out = new FileOutputStream(target)){
            CloudBlockBlob blob = container.getBlockBlobReference(key);
            blob.download(out);
            res.setStatus(200);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            res.setDescription(e.getMessage());
            return res;
        }
    }

    public void deleteByPrefix(String prefix){

        Iterable<ListBlobItem> r = container.listBlobs(prefix, true);
        for(ListBlobItem item : r){

//            container.getBlockBlobReference();

            StorageUri stor = item.getStorageUri();
            String path = stor.getPrimaryUri().getPath();
            String pp = path.substring(blobConfig.getContainerName().length() + 2);
            delete(pp);
        }
    }

    public static void main(String[] args) throws Exception {

        StorageServiceBlobImpl s = new StorageServiceBlobImpl();
        BlobConfig cfg = new BlobConfig();
//        cfg.setContainerName("myimages");
        cfg.setContainerName("frcontainer");
        cfg.setStorageConnectionString("");//设置连接字符串，在azure控制台获取
        s.init(cfg);
//        s.uploadFolder("live", new File("C:\\dev\\framework\\ffmpeg\\game\\video\\46970749-3eaa-40f0-9efe-3637187b7c5d\\1096000-1280x720"));
//        s.upload("dsadasd/dsadsad.jpg", new File("C:\\temp\\aa.jpg"));
        /*new Thread(()->{
            s.uploadFolder("vod", new File("C:\\dev\\framework\\ffmpeg\\game\\720"));
        }).start();

        new Thread(()->{
            s.upload("dsads/aa.mp4", new File("C:\\dev\\framework\\ffmpeg\\game\\aaa.mp4"));
        }).start();

        Thread.sleep(1000000);*/

//        s.copy("dsads/aa.mp4", "copy/das.mp4");

//        s.delete("copy/das.mp4");

        StringBuilder sb = new StringBuilder();
        sb.append("#EXTM3U\n");
        sb.append("#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=2048000,RESOLUTION=1280x720,NAME=\"720p HD\"\n");
        sb.append("1280x720/1280x720.m3u8\n");
        sb.append("#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=1024000,RESOLUTION=640x360,NAME=\"360p SD\"\n");
        sb.append("640x360/640x360.m3u8\n");
        sb.append("#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=512000,RESOLUTION=320x180,NAME=\"180p 3G\"\n");
        sb.append("320x180/320x180.m3u8\n");

//        s.upload("vod/dasda000/play.m3u8", sb.toString());

        s.download("v/BfIcA-EA-DcA/CEA/origin.mp4", new File("C:\\temp\\dsad.m3u8"));


        s.deleteByPrefix("v/");
//        s.download("dsads/aa.mp4", new File("C:\\dev\\framework\\ffmpeg\\game\\aaa23.mp4"));



    }
}
