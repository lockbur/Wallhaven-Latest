/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhao.spring.dao;

import com.anhao.spring.domain.PhotosTag;
import org.apache.ibatis.annotations.Insert;

/**
 * 壁纸标签 表
 *
 * @author Administrator
 */
public interface PhotosTagDao {

    public static final String SQL = "INSERT INTO biz.xx_photos_tag "
            + "(photo_id,tag_id)"
            + " VALUES (#{photoId},#{tagId})";

    @Insert(SQL)
    public int add(PhotosTag photosTag);
}
