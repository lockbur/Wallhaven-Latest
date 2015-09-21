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
		for (int i = 10000, c = 10050; i < c; i++) {
//			wallhavenJobCrawler.crawlByWallPaperId(i + "");
			
//			taskExecutor.execute(new crawlTask(i + ""));
		}

	}

}
