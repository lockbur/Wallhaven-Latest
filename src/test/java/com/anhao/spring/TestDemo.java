package com.anhao.spring;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anhao.spring.dao.JobPhotosDAO;
import com.anhao.spring.service.PhotosService;
import com.anhao.spring.task.crawlTask;
import com.anhao.spring.wallhaven.StorageService;
import com.anhao.spring.wallhaven.WallhavenJobCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class TestDemo {

    Logger logger = LoggerFactory.getLogger(TestDemo.class);
    @Resource
    WallhavenJobCrawler wallhavenJobCrawler;

    @Resource
    private JobPhotosDAO jobPhotosDAO;

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
