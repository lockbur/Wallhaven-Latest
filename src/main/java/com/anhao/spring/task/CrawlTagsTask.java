package com.anhao.spring.task;


import java.util.Date;
import java.util.UUID;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.anhao.spring.dao.TagDao;
import com.anhao.spring.domain.Tag;

/**
 * 解析标签
 * 
 * @author Administrator
 *
 */
public class CrawlTagsTask implements Runnable {
	Logger logger = LoggerFactory.getLogger(crawlTask.class);

	// private String wallpaperId;

	private int page;

	private TagDao tagDao;
	
	public CrawlTagsTask(int page,TagDao tagDao) {
		this.page = page;
		this.tagDao = tagDao;
	}

	@Override
	public void run() {
		String url = "http://alpha.wallhaven.cc/tags?page=" + page;
		Document doc;
		try {
			/*
			<ul id="taglist" class="taglist-tiles"> 
			<li>
				 <a class="tagname sfw" href="http://alpha.wallhaven.cc/tag/35687" >religious</a> 
				 <span class="stats"> 
				 <i class="fa fa-picture-o" title="Tagged Wallpapers"></i> 1 &nbsp; 
				 <i class="fa fa-bookmark" title="Subscribers"></i> 0 &nbsp; 
				 <i class="fa fa-eye" title="Total Views"></i> 200 
				 </span> 
				 <span class="creator">
				  Created by <a class="username usergroup-2" href="http://alpha.wallhaven.cc/user/HeZu">HeZu</a> 
				  <time title="Sat, Oct 10, 2015 11:35 PM" datetime="2015-10-10T23:35:18+00:00">2 days ago</time>
				 </span>
			  </li>
			 */
			Connection conn = Jsoup.connect(url);
			conn.userAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
			doc = conn.get();

			Elements links = doc.select("#taglist li");
			
			for (Element li : links) {
				
				Element tagEle = li.select(".tagname").first();
			
				Tag tag =new Tag();
				
				tag.setId(UUID.randomUUID().toString());
				tag.setCreate_date(new Date());
				tag.setModify_date(new Date());
				tag.setMember_id("1");
				tag.setName(tagEle.text());
				
				System.out.println("tag name ："+tagEle.text());
				tagDao.add(tag);
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
