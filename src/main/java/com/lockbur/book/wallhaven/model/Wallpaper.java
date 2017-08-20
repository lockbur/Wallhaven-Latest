package com.lockbur.book.wallhaven.model;

import com.lockbur.book.wallhaven.enums.Category;
import com.lockbur.book.wallhaven.enums.Purity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 图片信息
 */
@ToString
public class Wallpaper implements Serializable {

    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private Resolution resolution;

    @Setter
    @Getter
    private List<Color> colors;

    @Setter
    @Getter
    private List<Tag> tags;

    @Setter
    @Getter
    private Purity purity;

    @Setter
    @Getter
    private User user;

    @Setter
    @Getter
    private Category category;

    @Setter
    @Getter
    private Double size;

    @Setter
    @Getter
    private Integer viewsCount;

    @Setter
    @Getter
    private Integer favoritesCount;

    @Setter
    @Getter
    /*原图URL*/
    private String imageUrl;

    @Setter
    @Getter
    private List<WallpaperCollection> collections;

}
