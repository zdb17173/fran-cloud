package org.fran.cloud.azure.mediaservice;

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.media.MediaConfiguration;
import com.microsoft.windowsazure.services.media.MediaContract;
import com.microsoft.windowsazure.services.media.MediaService;
import com.microsoft.windowsazure.services.media.WritableBlobContainerContract;
import com.microsoft.windowsazure.services.media.authentication.AzureAdClientSymmetricKey;
import com.microsoft.windowsazure.services.media.authentication.AzureAdTokenCredentials;
import com.microsoft.windowsazure.services.media.authentication.AzureAdTokenProvider;
import com.microsoft.windowsazure.services.media.authentication.AzureEnvironments;
import com.microsoft.windowsazure.services.media.models.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AzureMediaService {
    private static String tenant = "cgtn.partner.onmschina.cn";
    private static String clientId = "afaf437d-9933-45de-b1e5-086bf6f2a752";
    private static String clientKey = "/3ZfR2FO39gSTQJz2w4xoJmY9RmiL7lvmbD+AVLUBGk=";
    private static String restApiEndpoint = "https://cgtntestmediaservice.restv2.chinaeast.media.chinacloudapi.cn/api/";
    private static String preferredEncoder = "Media Encoder Standard";
    private static String encodingPreset = "Content Adaptive Multiple Bitrate MP4";

    MediaContract mediaService;

    public void init() throws MalformedURLException, URISyntaxException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        AzureAdTokenCredentials credentials = new AzureAdTokenCredentials(
                tenant,
                new AzureAdClientSymmetricKey(clientId, clientKey),
                AzureEnvironments.AZURE_CHINA_CLOUD_ENVIRONMENT);

        AzureAdTokenProvider provider = new AzureAdTokenProvider(credentials, executorService);

        Configuration configuration = MediaConfiguration.configureWithAzureAdTokenProvider(
                new URI(restApiEndpoint),
                provider);

        mediaService = MediaService.create(configuration);

    }

    //创建Locator（发布） 获取播放地址
    public String getPlayUrlAndCreateLocator(JobInfo jobInfo) throws ServiceException {

        ListResult<AssetInfo> outputAssets = mediaService.list(Asset.list(jobInfo.getInputAssetsLink()));
        AssetInfo assetInfo = outputAssets.get(0);

        String streamingAssetFileName = null;
        ListResult<AssetFileInfo> file = mediaService.list(AssetFile.list(assetInfo.getAssetFilesLink()));
        for(AssetFileInfo f : file){
            if(f.getName().endsWith(".mp4")){
                String assetFilename = f.getName();
//                streamingAssetFileName = assetFilename;
                int posDot = assetFilename.lastIndexOf('.');
                if ( posDot != -1 ) {
                    streamingAssetFileName = assetFilename.substring(0, posDot) + ".ism";
                } else {
                    streamingAssetFileName = assetFilename + ".ism";
                }
            }

            System.out.println(f.getName());
        }

        AccessPolicyInfo originAccessPolicy;
        LocatorInfo originLocator = null;

        // Create a 30-day read only AccessPolicy
        double durationInMinutes = 60 * 24 * 30;
        originAccessPolicy = mediaService.create(
                AccessPolicy.create("Streaming policy", durationInMinutes, EnumSet.of(AccessPolicyPermission.READ)));

        // Create a Locator using the AccessPolicy and Asset
        originLocator = mediaService
                .create(Locator.create(originAccessPolicy.getId(), assetInfo.getId(), LocatorType.OnDemandOrigin));

        // Create a Smooth Streaming base URL
        return originLocator.getPath() + streamingAssetFileName + "/manifest(format=m3u8-aapl-v3)";
    }

    //获取资产信息
    public AssetInfo getAsset(String assetId) throws ServiceException {
        AssetInfo r = mediaService.get(Asset.get(assetId));
        /*ListResult<LocatorInfo> listLocator = mediaService.list(Locator.list(r.getLocatorsLink()));
        LocatorInfo sas = null;
        for(LocatorInfo locator : listLocator){
            if(locator.getLocatorType() == LocatorType.SAS){
                sas = locator;
            }
        }

        String baseUri = sas.getBaseUri();
        String token = sas.getContentAccessToken();

        ListResult<AssetFileInfo> assetFiles = mediaService.list(AssetFile.list(r.getAssetFilesLink()));
        for (AssetFileInfo file : assetFiles) {
            System.out.println(baseUri + "/" + file.getName() + token);
        }*/

        return r;
    }

    //上传到资产中
    public AssetInfo uploadToAsset(String assetName, String tempFile) throws ServiceException, FileNotFoundException, NoSuchAlgorithmException {
        AssetInfo resultAsset;

        // Create an Asset
        resultAsset = mediaService.create(Asset.create().setName(assetName).setAlternateId("altId"));
        System.out.println("Created Asset " + assetName);

        uploadAsset(resultAsset, tempFile);

        return resultAsset;
    }

    private String uploadAsset(AssetInfo asset, String fileName)
            throws ServiceException, FileNotFoundException, NoSuchAlgorithmException {

        WritableBlobContainerContract uploader;
        AccessPolicyInfo uploadAccessPolicy;
        LocatorInfo uploadLocator = null;

        // Create an AccessPolicy that provides Write access for 15 minutes
        uploadAccessPolicy = mediaService
                .create(AccessPolicy.create("uploadAccessPolicy", 15.0, EnumSet.of(AccessPolicyPermission.WRITE)));

        // Create a Locator using the AccessPolicy and Asset
        uploadLocator = mediaService
                .create(Locator.create(uploadAccessPolicy.getId(), asset.getId(), LocatorType.SAS));

        // Create the Blob Writer using the Locator
        uploader = mediaService.createBlobWriter(uploadLocator);

        File file = new File(fileName);

        // The local file that will be uploaded to your Media Services account
        InputStream input = new FileInputStream(file);

        // Upload the local file to the media asset
        String assetFilename = file.getName();
        uploader.createBlockBlob(assetFilename, input);

        // Inform Media Services about the uploaded files
        mediaService.action(AssetFile.createFileInfos(asset.getId()));
        System.out.println("Uploaded Asset File " + fileName);

        mediaService.delete(Locator.delete(uploadLocator.getId()));
        mediaService.delete(AccessPolicy.delete(uploadAccessPolicy.getId()));

        return assetFilename;
    }

    //编码
    public JobInfo encoding(AssetInfo assetInfo) throws ServiceException {

        String encodedAssetName = assetInfo.getName() + "-encoding";

        // Retrieve the list of Media Processors that match the name
        ListResult<MediaProcessorInfo> mediaProcessors = mediaService
                .list(MediaProcessor.list().set("$filter", String.format("Name eq '%s'", preferredEncoder)));

        // Use the latest version of the Media Processor
        MediaProcessorInfo mediaProcessor = null;
        for (MediaProcessorInfo info : mediaProcessors) {
            if (null == mediaProcessor || info.getVersion().compareTo(mediaProcessor.getVersion()) > 0) {
                mediaProcessor = info;
            }
        }

        // Create a task with the specified Media Processor
        String taskXml = "<taskBody><inputAsset>JobInputAsset(0)</inputAsset>"
                + "<outputAsset assetCreationOptions=\"0\"" // AssetCreationOptions.None
                + " assetName=\"" + encodedAssetName + "\">JobOutputAsset(0)</outputAsset></taskBody>";

//        encodingPreset = "Content Adaptive Multiple Bitrate MP4";
        Task.CreateBatchOperation task = Task.create(mediaProcessor.getId(), taskXml)
                .setConfiguration(encodingPreset).setName("Encoding");

        Job.Creator jobCreator = Job.create()
                .setName(assetInfo.getName() + "-encoding-" + encodingPreset)
                .addInputMediaAsset(assetInfo.getId()).setPriority(2).addTaskCreator(task);
        JobInfo job = mediaService.create(jobCreator);

        return job;
    }

    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        AzureMediaService s = new AzureMediaService();
        s.init();
        try {
            AssetInfo info = null;
//          info = s.getAsset("nb:cid:UUID:7be14638-8152-436e-a677-e9da3a0c2568");
//            info = s.getAsset("nb:cid:UUID:a5828599-76c4-4b3f-8786-ea0a55d73393");

            info = s.getAsset("nb:cid:UUID:34bbb4e3-5109-49a5-840a-dab9a9fdf654");
//            info = s.uploadToAsset("test03", "C:\\Users\\fran\\Desktop\\cms测试数据\\000000001.mp4");


            JobInfo job = s.encoding(info);
            System.out.println("jobId:[" + job.getId() + "]");

            String playUrl = s.getPlayUrlAndCreateLocator(job);
            System.out.println("playUrl:[" + playUrl + "]");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
