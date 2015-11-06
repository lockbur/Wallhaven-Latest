/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhao.spring.dao;

import com.anhao.spring.domain.PhotosColors;
import org.apache.ibatis.annotations.Insert;

/**
 * 操作壁纸颜色信息
 *
 * @author Administrator
 */
public interface PhotosColorsDAO {

    @Insert("INSERT INTO biz.xx_photos_colors "
            + "(id,red,green,blue,color,photos_id,create_date,modify_date)"
            + " VALUES (#{id},#{green},#{blue},#{color},#{photos_id},#{create_date},#{modify_date})")
    public int add(PhotosColors photosColors);
}
