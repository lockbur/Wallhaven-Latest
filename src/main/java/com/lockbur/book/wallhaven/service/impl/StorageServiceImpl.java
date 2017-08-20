package com.lockbur.book.wallhaven.service.impl;

import java.io.File;

import com.lockbur.book.wallhaven.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 存储到fastdfs
 *
 * @author Administrator
 */
@Service("storageServiceImpl")
public class StorageServiceImpl implements StorageService {

    Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
    
    @Override
    public String upload(File file) {
        return "test";
//        try {
//            FastdfsClient fastdfsClient = FastdfsClientFactory.getFastdfsClient("FastdfsClient.properties");
//            String fileId = fastdfsClient.upload(file);
//            
//            // return fileId;//
//            //logger.info("fastdfs upload file path : {}", fileId);
//            return fileId;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
    }

}
