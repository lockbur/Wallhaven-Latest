package com.lockbur.book.wallhaven.task;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据原有的wallhaven的内容添加标签
 *
 * @author Administrator
 */
public class CrawlAddTagsTask implements Runnable {

    Logger logger = LoggerFactory.getLogger(crawlTask.class);

    // private String wallpaperId;
    private String wallpaperId;
//    private PhotosTagDao photosTagDao;
//    private JobPhotosDAO jobPhotosDAO;
//    private TagDao tagDao;
//
//    public CrawlAddTagsTask(String wallpaperId, PhotosTagDao photosTagDao, JobPhotosDAO jobPhotosDAO, TagDao tagDao) {
//        this.wallpaperId = wallpaperId;
//        this.photosTagDao = photosTagDao;
//        this.jobPhotosDAO = jobPhotosDAO;
//        this.tagDao = tagDao;
//    }

    @Override
    public void run() {
        String url = "http://alpha.wallhaven.cc/wallpaper/" + wallpaperId;
        Document doc;
        try {
            /*
             <div class="sidebar-section" data-storage-id="showcase-tags">
             <a class="sidebar-section-toggle"><i class="fa fa-fw"></i></a>
             <h2>Tags</h2>
             <ul id="tags">
             <li class="tag tag-sfw" id="tag-222" data-tag-id="222"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/222">women</a></li>
             <li class="tag tag-sketchy" id="tag-424" data-tag-id="424"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/424">model</a></li>
             <li class="tag tag-sfw" id="tag-486" data-tag-id="486"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/486">face</a></li>
             <li class="tag tag-sfw" id="tag-164" data-tag-id="164"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/164">blonde</a></li>
             <li class="tag tag-sfw" id="tag-188" data-tag-id="188"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/188">blue eyes</a></li>
             <li class="tag tag-sfw" id="tag-21919" data-tag-id="21919"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/21919">bare shoulders</a></li>
             <li class="tag tag-sfw" id="tag-623" data-tag-id="623"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/623">necklace</a></li>
             <li class="tag tag-sfw" id="tag-13607" data-tag-id="13607"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/13607">hands on head</a></li>
             <li class="tag tag-sfw" id="tag-3974" data-tag-id="3974"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/3974">white clothing</a></li>
             <li class="tag tag-sfw" id="tag-872" data-tag-id="872"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/872">women outdoors</a></li>
             <li class="tag tag-sfw" id="tag-4718" data-tag-id="4718"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/4718">portrait</a></li>
             <li class="tag tag-sfw" id="tag-109" data-tag-id="109"><a class="tagname" rel="tag" href="http://alpha.wallhaven.cc/tag/109">photography</a></li>
             </ul></div>
             */
            Connection conn = Jsoup.connect(url);
            conn.userAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
            doc = conn.get();

            Elements links = doc.select("#tags li");

            for (Element li : links) {
                //系统存储的id是uuid 而不是wallhaven的ID
//                String photosId = jobPhotosDAO.findByWallpaperId(wallpaperId);
                //获得tag的UUID
                Element tagEle = li.select(".tagname").first();

//                String TagId = tagDao.findByTagName(tagEle.text());

                System.out.println("wallpaperId:" + wallpaperId + "====tag name ：" + tagEle.text());
//                PhotosTag photosTag = new PhotosTag();

//                photosTag.setPhotoId(photosId);
//                photosTag.setTagId(TagId);

//                photosTagDao.add(photosTag);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
