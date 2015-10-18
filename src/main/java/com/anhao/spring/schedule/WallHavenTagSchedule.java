package com.anhao.spring.schedule;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import com.anhao.spring.dao.TagDao;
import com.anhao.spring.task.CrawlTagsTask;


@Component
public class WallHavenTagSchedule {
	Logger logger = LoggerFactory.getLogger(WallHavenSchedule.class);
	/**
	 * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下） cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23)
	 * *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)
	 */
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;

	@Resource
	private TagDao tagDAO;

	private int page = 1;

	/**
	 * 截至时间20151013
	 */
	private int totalPages = 600;

	// 每20秒执行一次
	//@Scheduled(cron="*/1 * * * * ?")
	public void myTest() {
		System.out.println("=============================================");
		System.out.println("ActiveCount :"+taskExecutor.getActiveCount());
		System.out.println("PoolSize :"+taskExecutor.getPoolSize());
		System.out.println("CorePoolSize :"+taskExecutor.getCorePoolSize());
		System.out.println("=============================================");
		
		if(taskExecutor.getActiveCount()<10){
			System.out.println("当前页:"+page);
			
			if(page<=totalPages){
				for (int i = page; i < page+5; i++) {
					taskExecutor.execute(new CrawlTagsTask(i,tagDAO));
				}
				page=page+5;
			}else {
				logger.info("抓取页码结束 {}",page);
			}
		}
	}
}
