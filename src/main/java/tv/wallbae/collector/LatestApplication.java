package tv.wallbae.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by wangkun23 on 2017/9/20.
 */
@SpringBootApplication
public class LatestApplication {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(150);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(60);
        return executor;
    }

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(LatestApplication.class, args);
       // DownloadService downloadService = context.getBean(DownloadService.class);

//        try {
//            //开始执行下载器
//            //downloadService.download();
//        } catch (IOException | URISyntaxException | ParserConfigurationException | SAXException e) {
//            e.printStackTrace();
//        }

    }
}
