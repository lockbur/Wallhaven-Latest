/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhao.spring.schedule;

import com.anhao.spring.dao.JobPhotosDAO;
import com.anhao.spring.dao.PhotosTagDao;
import com.anhao.spring.dao.TagDao;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import javax.annotation.Resource;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 构建图片索引
 *
 * @author Administrator
 */
@Component
public class BuildIndexSchedule {

    Logger logger = LoggerFactory.getLogger(BuildIndexSchedule.class);

    private static HttpSolrServer solrServer = null;

    @Resource
    private JobPhotosDAO jobPhotosDAO;

    @Resource
    private TagDao tagDAO;

    @Resource
    private PhotosTagDao photostagDAO;

    public BuildIndexSchedule() {
        if (solrServer == null) {
            try {
                // configure a server object with actual solr values.
                solrServer = new HttpSolrServer("http://localhost:9090/solr");
                solrServer.setParser(new XMLResponseParser());
                solrServer.setSoTimeout(5000);
                solrServer.setConnectionTimeout(5000);
                // Other commonly used properties
                solrServer.setDefaultMaxConnectionsPerHost(100);
                solrServer.setMaxTotalConnections(100);
                solrServer.setMaxRetries(1); // defaults to 0. > 1 not recommended.
            } catch (Exception exc) {
                logger.error(" Exception in getting Solr Connection: " + exc.getMessage());
                exc.printStackTrace();
            }

        }
    }

//    @Scheduled(cron = "*/1 * * * * ?")
    public void build() {
        SolrInputDocument doc = new SolrInputDocument();
        String id = UUID.randomUUID().toString();
        doc.addField("id", id);
        logger.info("id {}", id);

        doc.addField("title", "中央全面深化改革领导小组");
        String tags = "shaved vagina,Castlevania: Mirror of Fate, Kousaka Tamaki";

        doc.addField("tags", tags);

        doc.addField("large", "http://localhost:9090/solr/#/collection1/query");
        doc.addField("medium", "http://localhost:9090/solr/#/collection1/query");
        doc.addField("thumbnail", "http://localhost:9090/solr/#/collection1/query");
        doc.addField("source", "http://localhost:9090/solr/#/collection1/query");
        doc.addField("member_id", "1");
        doc.addField("member_name", "lockbur");
        doc.addField("wallhaven", "4535323");
        doc.addField("create_date", new Date());
        doc.addField("modify_date", new Date());

        try {
            solrServer.add(doc);
            solrServer.commit();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
