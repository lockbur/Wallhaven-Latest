package com.anhao.spring.task;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anhao.spring.dao.JobPhotosDAO;
import com.anhao.spring.domain.Photos;
import com.anhao.spring.wallhaven.StorageService;

public class crawlTask implements Runnable {

    Logger logger = LoggerFactory.getLogger(crawlTask.class);

    //private String wallpaperId;
    private int page;

    private JobPhotosDAO jobPhotosDAO;

    private StorageService storageService;

//	public crawlTask(String wallpaperId, JobPhotosDAO jobPhotosDAO,
//			StorageService storageService) {
//		this.wallpaperId = wallpaperId;
//		this.jobPhotosDAO = jobPhotosDAO;
//		this.storageService = storageService;
//	}
    public crawlTask(int page, JobPhotosDAO jobPhotosDAO,
            StorageService storageService) {
        this.page = page;
        this.jobPhotosDAO = jobPhotosDAO;
        this.storageService = storageService;
    }

    @Override
    public void run() {
        String url = "http://alpha.wallhaven.cc/latest?page=" + page;
        Document doc;
        try {
            Connection conn = Jsoup.connect(url);
            conn.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
            doc = conn.get();

            Elements links = doc.select("section ul li");
            for (Element li : links) {
                Element figure = li.child(0);
                String wallpaperId = figure.attr("data-wallpaper-id");

                /**
                 * 判断是否已经抓取
                 */
                if (jobPhotosDAO.findByWallpaperId(wallpaperId) == null) {
                    logger.info("wallpapers id {} thumbnail  exist.", wallpaperId);
                    continue;
                }

                String thumbnail = "http://alpha.wallhaven.cc/wallpapers/thumb/small/th-"
                        + wallpaperId + ".jpg";

                String full = "http://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-"
                        + wallpaperId + ".jpg";

                boolean smallStatus = getImages(thumbnail, "/tmp/small"
                        + wallpaperId + ".jpg");
                boolean fullStatus = getImages(full, "/tmp/full"
                        + wallpaperId + ".jpg");

                if (smallStatus && fullStatus) {
                    File thumbnailFile = new File("/tmp/small" + wallpaperId
                            + ".jpg");
                    String thumbnailPath = storageService.upload(thumbnailFile);

                    thumbnailFile.delete();

                    File sourceFile = new File("/tmp/full" + wallpaperId
                            + ".jpg");
                    String sourceFilePath = storageService.upload(sourceFile);

                    sourceFile.delete();

                    Photos photos = new Photos();
                    photos.setId(UUID.randomUUID().toString());
                    photos.setCreate_date(new Date());
                    photos.setModify_date(new Date());

                    photos.setLarge(sourceFilePath);
                    photos.setMedium(thumbnailPath);
                    photos.setOrders(1);
                    photos.setSource(sourceFilePath);
                    photos.setThumbnail(thumbnailPath);
                    photos.setAlbum_id("ff8081814f7e13d8014f7e18a95a0000");
                    photos.setMember_id("1");
                    photos.setTitle(wallpaperId);
                    photos.setWallhaven(wallpaperId);
                    photos.setStorage_host("http://123.57.240.11");

                    jobPhotosDAO.add(photos);

                } else {
                    logger.info("wallpapers id {} thumbnail or fullImage not exist.", wallpaperId);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param urlPath
     * @param fileName
     * @throws Exception
     */
    public boolean getImages(String urlPath, String fileName) throws Exception {
        // System.out.println("urlPath: " + urlPath);
        Connection conn = Jsoup
                .connect(urlPath)
                .userAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
        try {
            Response response = conn.ignoreContentType(true).execute();
            if (response.statusCode() == 200) {
                byte[] data = response.bodyAsBytes();
                FileOutputStream outputStream = new FileOutputStream(fileName);
                // File outFile=new File(outputStream);
                outputStream.write(data);
                outputStream.close();
                return true;
            } else {

                logger.error("download {} 404 ", urlPath);
            }

        } catch (Exception e) {
            logger.error("download error message: {}", e.getMessage());
            return false;
        }
        return false;
    }
}
