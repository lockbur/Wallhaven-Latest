package tv.wallbae.collector;

import javax.annotation.Resource;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tv.wallbae.collector.service.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.task.TaskExecutor;

import tv.wallbae.collector.service.PhotosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RunWith(SpringRunner.class)
@SpringBootTest
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
        logger.info("photosService is {}", photosService);
    }

}
