package com.anhao.spring.wallhaven;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.anhao.spring.dao.JobPhotosDAO;
import com.anhao.spring.domain.Photos;

/**
 * :wallhaven
 *
 * @author youyou
 */
@Component
public class WallhavenJobCrawler {

	@Resource
	private JobPhotosDAO jobPhotosDAO;

	@Resource
	private StorageService storageService;

	@Resource
	TaskExecutor taskExecutor;

	public void crawl(int page) {
		String url = "http://alpha.wallhaven.cc/latest?page=" + page;
		Document doc;
		try {
			Connection conn = Jsoup
					.connect(url)
					.userAgent(
							"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
			doc = conn.get();
			Elements links = doc.select("section ul li");
			for (Element li : links) {
				Element figure = li.child(0);
				String wallpaperId = figure.attr("data-wallpaper-id");
				// parserProperties(figure.attr("data-wallpaper-id"));
				String thumbnail = "http://alpha.wallhaven.cc/wallpapers/thumb/small/th-"
						+ wallpaperId + ".jpg";
				String full = "http://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-"
						+ wallpaperId + ".jpg";
				try {
					boolean smallStatus = getImages(thumbnail, "D:/tmp/small"
							+ wallpaperId + ".jpg");
					boolean fullStatus = getImages(full, "D:/tmp/full"
							+ wallpaperId + ".jpg");

					if (smallStatus && fullStatus) {

						File thumbnailFile = new File("D:/tmp/small"
								+ wallpaperId + ".jpg");
						String thumbnailPath = storageService
								.upload(thumbnailFile);
						thumbnailFile.delete();
						File sourceFile = new File("D:/tmp/full" + wallpaperId
								+ ".jpg");
						String sourceFilePath = storageService
								.upload(sourceFile);
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
						photos.setStorage_host("http://aboutdata.me");

						jobPhotosDAO.add(photos);
						System.out.println("end .....");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param wallpaperId
	 */
	public void crawlByWallPaperId(String wallpaperId) {
		addCrawlTask(wallpaperId);
	}

	/**
	 * @param urlPath
	 * @param fileName
	 * @throws Exception
	 */
	public boolean getImages(String urlPath, String fileName) throws Exception {
		System.out.println("urlPath: " + urlPath);
		Connection conn = Jsoup
				.connect(urlPath)
				.userAgent(
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
		try {
			Response response = conn.ignoreContentType(true).execute();
	
			System.out.println("conn.getResponseCode():  "
					+ response.statusCode());
			if (response.statusCode() == 200) {
				byte[] data = response.bodyAsBytes();
				FileOutputStream outputStream = new FileOutputStream(fileName);
				// File outFile=new File(outputStream);
				outputStream.write(data);
				outputStream.close();
				return true;
			}

		} catch (Exception e) {
			System.out.println("cant access ");
			return false;
		}
		return false;
	}

	/**
	 * 
	 * @param wallpaperId
	 */
	private void addCrawlTask(final String wallpaperId) {
		System.out.println("wallpaperId ： "+wallpaperId);
		String thumbnail = "http://alpha.wallhaven.cc/wallpapers/thumb/small/th-"+ wallpaperId + ".jpg";
		String full = "http://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-"+ wallpaperId + ".jpg";
		try {
			boolean smallStatus = getImages(thumbnail,"D:/tmp/small" + wallpaperId + ".jpg");
			boolean fullStatus = getImages(full, "D:/tmp/full"+ wallpaperId + ".jpg");

			if (smallStatus && fullStatus) {

				File thumbnailFile = new File("D:/tmp/small"+ wallpaperId + ".jpg");
				String thumbnailPath = storageService.upload(thumbnailFile);
				
				thumbnailFile.delete();
				
				File sourceFile = new File("D:/tmp/full"+ wallpaperId + ".jpg");
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
				photos.setStorage_host("http://aboutdata.me");

				jobPhotosDAO.add(photos);
				System.out.println("end .....");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
