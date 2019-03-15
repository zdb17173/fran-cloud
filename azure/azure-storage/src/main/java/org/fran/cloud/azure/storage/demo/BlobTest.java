package org.fran.cloud.azure.storage.demo;

import com.microsoft.azure.storage.AccessCondition;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class BlobTest {

    public static final String storageConnectionString ="";//设置连接字符串

    public static void main(String[] args){
        try {
            CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient serviceClient = account.createCloudBlobClient();

            // Container name must be lower case.
            CloudBlobContainer container = serviceClient.getContainerReference("myimages");
            container.createIfNotExists();

            // Upload an image file.
            CloudBlockBlob blob = container.getBlockBlobReference("image1.jpg");

            DateFormat gmtDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);

            File sourceFile = new File("c:\\temp\\aa.jpg");
            blob.upload(
                    new FileInputStream(sourceFile),
                    sourceFile.length());

            BlobProperties properties = blob.getProperties();
            properties.setContentType("image/jpg");
            properties.setCacheControl("max-age=3600");
            blob.uploadProperties();

            /*BlobRequestOptions req = new BlobRequestOptions();
            blob.upload(
                    new FileInputStream(sourceFile),
                    sourceFile.length(),
                    AccessCondition.generateIfExistsCondition(),
                    req,
                    o);*/

            //Download the image file.
//            File destinationFile = new File(sourceFile.getParentFile(), "image1Download.tmp");
//            blob.downloadToFile(destinationFile.getAbsolutePath());

            String prefix = "DSC";
            for (ListBlobItem item : container.listBlobs(prefix, true,
                    EnumSet.allOf(BlobListingDetails.class), null, null)) {
                CloudBlockBlob b = (CloudBlockBlob) item;
                System.out.println(b.getName());
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            System.out.print("FileNotFoundException encountered: ");
            System.out.println(fileNotFoundException.getMessage());
            System.exit(-1);
        }
        catch (StorageException storageException) {
            storageException.printStackTrace();
            System.out.print("StorageException encountered: ");
            System.out.println(storageException.getMessage());
            System.exit(-1);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.print("Exception encountered: ");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
