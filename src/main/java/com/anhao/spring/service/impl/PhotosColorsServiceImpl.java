/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhao.spring.service.impl;

import com.anhao.spring.dao.JobPhotosDAO;
import com.anhao.spring.dao.PhotosColorsDAO;
import com.anhao.spring.domain.PhotosColors;
import com.anhao.spring.service.PhotosColorsService;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import me.croma.image.AWTImage;
import me.croma.image.Color;
import me.croma.image.ColorPicker;
import me.croma.image.Image;
import me.croma.image.KMeansColorPicker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class PhotosColorsServiceImpl implements PhotosColorsService {

    Logger logger = LoggerFactory.getLogger(PhotosColorsServiceImpl.class);

    @Resource
    private PhotosColorsDAO photosColorsDAO;

    @Override
    public void generateColors(File file, String photos_id) {
        if (file != null) {
            try {
                Image img = new AWTImage(file);
                ColorPicker km = new KMeansColorPicker();
                // ColorPicker km = new DBScanColorPicker();
                //截取算法不一样
                // ColorPicker km = new MedianCutColorPicker();
                //6 取出6种颜色
                List<Color> list = km.getUsefulColors(img, 6);
                //file.delete();
                for (Color c : list) {
                    PhotosColors colors = new PhotosColors();
                    colors.setId(UUID.randomUUID().toString());
                    colors.setBlue(c.getBlue());
                    colors.setGreen(c.getGreen());
                    colors.setRed(c.getRed());
                    colors.setColor(c.toHexString());
                    colors.setPhotos_id(photos_id);
                    
                    colors.setCreate_date(new Date());
                    colors.setModify_date(new Date());
                    photosColorsDAO.add(colors);
                }
                file.delete();
            } catch (IOException ex) {
                logger.error("photos_id: {} generateColors error {}", photos_id, ex);
            }
        }
    }
}
