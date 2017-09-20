package tv.wallbae.collector.processor;

import tv.wallbae.collector.enums.Category;
import tv.wallbae.collector.enums.Purity;
import tv.wallbae.collector.model.Tag;
import tv.wallbae.collector.model.Wallpaper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则 先解析分页页面 ，然后根据分页页面的URL 去抓去wallpaper 的详情页面，然后把需要的数据解析出来
 * Created by wangkun23 on 2017/8/18.
 */
@Component
public class WallpaperProcessor implements PageProcessor {

    final Logger logger = LoggerFactory.getLogger(WallpaperProcessor.class);

    private static final String PAGE_URL = "^https\\://alpha\\.wallhaven\\.cc/random\\?page";
    private static final String WALLPAPER_URL = "^https\\://alpha\\.wallhaven\\.cc/wallpaper/";


    @Override
    public void process(Page page) {

        //如果分页 就把页面加入详情页面继续抓去
        if (page.getUrl().regex(PAGE_URL).match()) {
            List<Selectable> figureElements = page.getHtml().xpath("//section/ul/li").nodes();
            for (Selectable figure : figureElements) {
                //logger.info("##{} ", figure);
                String wallpaperId = figure.xpath("//figure/@data-wallpaper-id").get();
                logger.info("data-wallpaper-id {} ", wallpaperId);
                page.addTargetRequest(new Request("https://alpha.wallhaven.cc/wallpaper/" + wallpaperId));

            }
        }

        //如果是详情 解析页面数据、
        //https://alpha.wallhaven.cc/wallpaper/51351
        if (page.getUrl().regex(WALLPAPER_URL).match()) {
            Wallpaper wallpaper = new Wallpaper();
            //1 原图地址

            //2 类别
            List<Selectable> dl = page.getHtml().xpath("//div/[@data-storage-id='showcase-info']/dl/dd/text()").nodes();
            if (dl.size() != 0) {
                String categoryName = dl.get(1).get();
                Category category = StringUtils.equalsIgnoreCase(categoryName, "GENERAL") ? Category.GENERAL
                        : StringUtils.equalsIgnoreCase(categoryName, "ANIME") ? Category.ANIME
                        : StringUtils.equalsIgnoreCase(categoryName, "PEOPLE") ? Category.PEOPLE
                        : null;
                logger.info("category {} ", category);
                wallpaper.setCategory(category);
            }

            //3 标签信息
            List<Selectable> tagLiElements = page.getHtml().xpath("//ul[@id='tags']/li").nodes();
            List<Tag> tags = new ArrayList<>(tagLiElements.size());
            for (Selectable li : tagLiElements) {
                String tagId = li.xpath("//li/@data-tag-id").get();
                String tagName = li.xpath("//li/a[@class='tagname']/text()").get();

                //region purity
                String liClassString = li.xpath("//li/@class").get();
                Purity purity = liClassString.contains("tag-sfw") ? Purity.SFW
                        : liClassString.contains("tag-sketchy") ? Purity.SKETCHY
                        : liClassString.contains("tag-nsfw") ? Purity.NSFW
                        : null;
                if (purity == null) {
                    throw new IllegalArgumentException("Could not parse purity of wallpaper thumbnail");
                }

                logger.info("tagId {} ", tagId);
                logger.info("tagName {} ", tagName);


                Tag tag = new Tag();
                tag.setId(Integer.valueOf(tagId));
                tag.setName(tagName);
                tag.setPurity(purity);

                tags.add(tag);
            }
            wallpaper.setTags(tags);

        }

    }

    @Override
    public Site getSite() {
        Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
        return site;
    }

    public static void main(String[] args) {
        //Spider.create(new WallpaperProcessor()).addUrl("https://alpha.wallhaven.cc/random?page=2").run();
        Spider.create(new WallpaperProcessor()).addUrl("https://alpha.wallhaven.cc/wallpaper/51351").run();
    }
}
