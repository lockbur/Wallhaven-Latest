package com.lockbur.book.wallhaven;

import javax.annotation.Resource;

import com.lockbur.book.wallhaven.service.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lockbur.book.wallhaven.service.PhotosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class TestDemo {

    Logger logger = LoggerFactory.getLogger(TestDemo.class);
    @Resource
    WallhavenJobCrawler wallhavenJobCrawler;


    @Resource
    private StorageService storageService;

    @Resource
    private PhotosService photosService;

    @Resource
    TaskExecutor taskExecutor;

    /**
     * 抓取 并解析网页内容
     */
    @Test
    public void crawl() {
        logger.info("photosService is {}",photosService);
    }

}
