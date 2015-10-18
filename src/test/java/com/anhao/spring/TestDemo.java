package com.anhao.spring;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anhao.spring.dao.JobPhotosDAO;
import com.anhao.spring.task.crawlTask;
import com.anhao.spring.wallhaven.StorageService;
import com.anhao.spring.wallhaven.WallhavenJobCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class TestDemo {

    @Resource
    WallhavenJobCrawler wallhavenJobCrawler;

    @Resource
    private JobPhotosDAO jobPhotosDAO;

    @Resource
    private StorageService storageService;

    @Resource
    TaskExecutor taskExecutor;

    /**
     * 抓取 并解析网页内容
     */
    @Test
    public void crawl() {
        String html = "<ul id=\"tags\"><li class=\"tag tag-sfw\" id=\"tag-222\" data-tag-id=\"222\"><a class=\"tagname\" rel=\"tag\" href=\"http://alpha.wallhaven.cc/tag/222\">women</a></li>";
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("#tags li");

        for (Element li : links) {

            Element tagEle = li.select(".tagname").first();

            System.out.println("tag name ：" + tagEle.text());

        }
    }

}
