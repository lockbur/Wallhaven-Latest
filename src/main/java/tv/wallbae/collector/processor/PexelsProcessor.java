package tv.wallbae.collector.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * https://www.pexels.com/
 * Created by wangkun23 on 2017/9/20.
 */
public class PexelsProcessor implements PageProcessor {
    final Logger logger = LoggerFactory.getLogger(PexelsProcessor.class);

    @Override
    public void process(Page page) {
        List<Selectable> articleList = page.getHtml().xpath("//div[@class='photos']/article[@class='photo-item']").nodes();

        for (Selectable article : articleList) {
            //<article class="photo-item">
            // <a href="/photo/blur-breakfast-close-up-dairy-product-376464/" title="">
            // <img width="573" height="350" style="background:rgb(136,125,116)"
            // class="photo-item__img" alt="Free stock photo of food, plate, blur, breakfast"
            // data-pin-media="https://images.pexels.com/photos/376464/pexels-photo-376464.jpeg?w=800&amp;h=1200&amp;fit=crop&amp;auto=compress&amp;cs=tinysrgb"
            // src="https://images.pexels.com/photos/376464/pexels-photo-376464.jpeg?h=350&amp;auto=compress&amp;cs=tinysrgb">
            // </a>
            // <button class="js-like js-like-376464 btn-like btn-like--small photo-item__info" data-photo-id="376464">
            //  <svg viewbox="0 0 100 100" class="icon-heart">
            //   <use xlink:href="#iconHeart"></use>
            //  </svg> </button>
            //</article>

            String href=article.xpath("//a/@href").get();///photo/blur-breakfast-close-up-dairy-product-376464/
            String id =article.xpath("//button/@data-photo-id").get();//https://images.pexels.com/photos/376464/pexels-photo-376464.jpeg?h=350&amp;auto=compress&amp;cs=tinysrgb
            String thumb =article.xpath("//img/@src").get();//376464

            //https://static.pexels.com/photos/54455/cook-food-kitchen-eat-54455.jpeg

            logger.info("href {}", href);
            logger.info("id {}", id);
            logger.info("thumb {}", thumb);
        }
    }

    @Override
    public Site getSite() {
        Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
        return site;
    }

    public static void main(String[] args) {
        //https://www.pexels.com/popular-searches/
        //https://www.pexels.com/popular-searches?page=9
        //https://www.pexels.com/search/food/?page=7
        Spider.create(new PexelsProcessor()).addUrl("https://www.pexels.com/search/food/").run();
    }
}
