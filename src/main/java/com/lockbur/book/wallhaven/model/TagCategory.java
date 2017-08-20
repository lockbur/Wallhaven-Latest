package com.lockbur.book.wallhaven.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * 标签分类
 */
@ToString
public class TagCategory implements Serializable {

    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private TagCategory parentCategory;
}
