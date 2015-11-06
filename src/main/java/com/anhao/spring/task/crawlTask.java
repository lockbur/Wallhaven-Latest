package com.anhao.spring.task;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.anhao.spring.service.PhotosService;

public class crawlTask implements Runnable {

    Logger logger = LoggerFactory.getLogger(crawlTask.class);

    //private String wallpaperId;
    private int page;

    private PhotosService photosService;

    public crawlTask(int page, PhotosService photosService) {
        this.page = page;
        this.photosService = photosService;
    }

    @Override
    public void run() {
        String url = "http://alpha.wallhaven.cc/latest?page=" + page;
        Document doc;
        try {
            Connection conn = Jsoup.connect(url);
            conn.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
            doc = conn.get();

            photosService.process(doc);
        } catch (Exception ex) {
            
            ex.printStackTrace();
        }
    }
}
