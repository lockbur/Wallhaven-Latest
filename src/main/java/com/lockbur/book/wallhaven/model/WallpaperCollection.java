package com.lockbur.book.wallhaven.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 用户收藏夹
 */
@ToString
public class WallpaperCollection implements Serializable {

    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private Long wallpapersCount;

    @Setter
    @Getter
    private Long viewsCount;

    @Setter
    @Getter
    private Long subscribersCount;

    @Setter
    @Getter
    private List<User> subscribers;
}
