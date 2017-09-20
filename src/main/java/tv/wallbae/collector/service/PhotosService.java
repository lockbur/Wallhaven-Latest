/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.wallbae.collector.service;

import org.jsoup.nodes.Document;

/**
 *
 * @author Administrator
 */
public interface PhotosService {

    public void process(Document doc);
}
