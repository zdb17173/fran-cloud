package org.fran.cloud.azure.storage.service;


import org.fran.cloud.azure.storage.service.bo.StorageResult;
import org.fran.cloud.azure.storage.service.config.BlobConfig;

import java.io.File;
import java.io.InputStream;

/**
 * @author fran
 * @Description
 * @Date 2019/1/2 10:16
 */
public interface StorageService {

    public StorageService init(BlobConfig blobConfig) throws Exception;
    public StorageResult upload(String key, String text);
    public StorageResult upload(String key, File sourceFile);
    public StorageResult upload(String key, long len, InputStream inputStream);
    public StorageResult delete(String key);
    public StorageResult copy(String source, String target);
    public void uploadFolder(String key, File folder);
    public StorageResult download(String key, File target);
    public BlobConfig getConfig();
}
