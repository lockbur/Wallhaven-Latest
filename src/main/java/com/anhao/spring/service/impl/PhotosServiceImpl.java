/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhao.spring.service.impl;

import com.anhao.spring.dao.JobPhotosDAO;
import com.anhao.spring.dao.PhotosTagDao;
import com.anhao.spring.dao.TagDao;
import com.anhao.spring.domain.Photos;
import com.anhao.spring.domain.PhotosTag;
import com.anhao.spring.service.PhotosColorsService;
import com.anhao.spring.service.PhotosService;
import com.anhao.spring.utils.EasyImage;
import com.anhao.spring.wallhaven.StorageService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class PhotosServiceImpl implements PhotosService {

    Logger logger = LoggerFactory.getLogger(PhotosServiceImpl.class);

    @Resource
    private JobPhotosDAO jobPhotosDAO;

    @Resource
    private TagDao tagDAO;

    @Resource
    private PhotosTagDao photostagDAO;

    @Resource
    private StorageService storageService;

    @Resource
    private PhotosColorsService photosColorsService;

    @Override
    public void process(Document doc) {
        Elements links = doc.select("section ul li");
        for (Element li : links) {
            Element figure = li.child(0);
            String wallpaperId = figure.attr("data-wallpaper-id");
            /**
             * 判断是否已经抓取
             */
            String tempUUID = jobPhotosDAO.findByWallpaperId(wallpaperId);

            if (StringUtils.isNotEmpty(tempUUID)) {
                logger.info("wallpapers id {} thumbnail  exist.", wallpaperId);
                //如果本地数据中已经存在该wallpaperID的数据 就不再处理
                continue;
            }

            String thumbnail = "http://alpha.wallhaven.cc/wallpapers/thumb/small/th-" + wallpaperId + ".jpg";
            String full = "http://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-" + wallpaperId + ".jpg";
            /**
             * 在上传到fastdfs时需要先写入到本地文件系统，等上传到fastdfs后删除该文件，默认存放到/tmp/
             */
            boolean smallStatus = download(thumbnail, "/tmp/small" + wallpaperId + ".jpg");
            boolean fullStatus = download(full, "/tmp/full" + wallpaperId + ".jpg");

            if (smallStatus && fullStatus) {
                File thumbnailFile = new File("/tmp/small" + wallpaperId + ".jpg");
                String thumbnailPath = storageService.upload(thumbnailFile);

                thumbnailFile.delete();

                File sourceFile = new File("/tmp/full" + wallpaperId + ".jpg");
                String sourceFilePath = storageService.upload(sourceFile);
                EasyImage easyImage = new EasyImage(sourceFile);

                //再删除之前先截取图片颜色
                //sourceFile.delete();
                String uuid = UUID.randomUUID().toString();
                Photos photos = new Photos();
                photos.setId(uuid);

                photos.setTitle(wallpaperId);
                photos.setWidth(easyImage.getWidth());
                photos.setHeight(easyImage.getHeight());
                photos.setSize(sourceFile.length());

                photos.setCreate_date(new Date());
                photos.setModify_date(new Date());

                photos.setLarge(sourceFilePath);
                photos.setMedium(thumbnailPath);
                photos.setOrders(1);
                photos.setSource(sourceFilePath);
                photos.setThumbnail(thumbnailPath);
                photos.setAlbum_id("ff8081814f7e13d8014f7e18a95a0000");
                photos.setMember_id("1");
                

                photos.setWallhaven(wallpaperId);
                photos.setStorage_host("http://123.57.240.11");
                jobPhotosDAO.add(photos);

                //2015-10-18添加 标签操作 开始
                getWallpaperTags(wallpaperId);
                //2015-10-18添加 标签操作 结束
                //生成颜色
                photosColorsService.generateColors(sourceFile, uuid);

            } else {
                logger.info("wallpapers id {} thumbnail or fullImage not exist.", wallpaperId);
            }
        }
    }

    /**
     * @param urlPath
     * @param fileName
     * @throws Exception
     */
    private boolean download(String urlPath, String fileName) {
        logger.info("urlPath: " + urlPath);
        Connection conn = Jsoup
                .connect(urlPath)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
        try {
            Connection.Response response = conn.ignoreContentType(true).execute();
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

    private Document getWallpaperHtmlDocument(String wallpaperUrl) {
        try {
            Connection connWallpaperDetail = Jsoup.connect(wallpaperUrl);
            connWallpaperDetail.userAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
            return connWallpaperDetail.get();
        } catch (IOException ex) {
            logger.error("getWallpaperHtmlDocument error message: {}", ex.getMessage());
        }
        return null;
    }

    private void getWallpaperTags(String wallpaperId) {
        String wallpaperUrl = "http://alpha.wallhaven.cc/wallpaper/" + wallpaperId;
        Document docDetails = getWallpaperHtmlDocument(wallpaperUrl);
        Elements Tags = docDetails.select("#tags li");
        for (Element tag : Tags) {
            //系统存储的id是uuid 而不是wallhaven的ID
            String photosId = jobPhotosDAO.findByWallpaperId(wallpaperId);
            //获得tag的UUID
            Element tagName = tag.select(".tagname").first();

            String TagId = tagDAO.findByTagName(tagName.text());

            System.out.println("wallpaperId:" + wallpaperId + "====tag name ：" + tagName.text());
            PhotosTag photosTag = new PhotosTag();

            photosTag.setPhotoId(photosId);
            photosTag.setTagId(TagId);
            photostagDAO.add(photosTag);
        }
    }
}
