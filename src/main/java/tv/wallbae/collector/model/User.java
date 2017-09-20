package tv.wallbae.collector.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户管理
 */
@ToString
public class User implements Serializable {

    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private String groupName;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private Long uploadedWallpapersCount;

    @Setter
    @Getter
    private Long favoriteWallpapersCount;

    @Setter
    @Getter
    private Long taggedWallpapersCount;

    @Setter
    @Getter
    private Long flaggedWallpapersCount;

    @Setter
    @Getter
    private Long subscribersCount;

    @Setter
    @Getter
    private Long profileViewsCount;

    @Setter
    @Getter
    private Long profileCommentsCount;

    @Setter
    @Getter
    private Long forumPostsCount;

    @Setter
    @Getter
    private Date lastActiveTime;

    @Setter
    @Getter
    private Date dateCreated;
}
