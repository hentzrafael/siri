package com.siri.backend.services;

import com.siri.backend.models.WebSiteCrawlModel;
import com.siri.backend.utils.Utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WebCrawler {
    private final Set<String> visitedUrls = new HashSet<>();
    private final Map<String, WebSiteCrawlModel> crawlResponses;
    private final Map<String, String> websites;

    private final String baseUrl;
    private final String id;

    public WebCrawler(String id, Map<String, WebSiteCrawlModel> crawlResponses, Map<String, String> websites, Environment env) {
        this.id = id;
        this.crawlResponses = crawlResponses;
        this.websites = websites;
        this.baseUrl = env.getBaseUrl();
    }

    //API method to search for a keyword in the website
    public void search(String searchTerm) throws IOException {
        crawl(baseUrl, searchTerm, this.crawlResponses.get(id).getUrls(), this.websites);
    }

    //Recursive method to crawl the website links
    private void crawl(String url, String searchTerm, Set<String> resultUrls, Map<String, String> websites) throws IOException {
        //Prevent infinite loop
        if (visitedUrls.contains(url)) {
            return;
        }
        visitedUrls.add(url);

        //Use cached content if available
        String content;
        if (websites.containsKey(url)) {
            content = websites.get(url);
        } else {
            content = Utils.getContent(url).toLowerCase();
            if (content.isEmpty()) {
                return;
            }
            websites.put(url, content);
        }

        if (content.contains(searchTerm)) {
            resultUrls.add(url);
        }

        Set<String> links = Utils.extractLinks(content);

        for (String link : links) {
            //These are not valid links to crawl
            if (link.startsWith("#") || link.startsWith("mailto:") || link.startsWith("tel:")) {
                continue;
            }

            String absoluteLink = Utils.resolveUrl(url, link);
            if (visitedUrls.contains(absoluteLink) || !absoluteLink.startsWith(baseUrl)) {
                continue;
            }

            crawl(absoluteLink, searchTerm, resultUrls, websites);
        }
    }


}