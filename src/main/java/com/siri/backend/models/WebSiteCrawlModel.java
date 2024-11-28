package com.siri.backend.models;

import java.util.Set;

public class WebSiteCrawlModel {
    private final String id;
    private final String status;
    private final Set<String> urls;
    private final String keyword;

    public WebSiteCrawlModel(String id, String keyword, String status, Set<String> urls) {
        this.id = id;
        this.keyword = keyword;
        this.status = status;
        this.urls = urls;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Set<String> getUrls() {
        return urls;
    }
}
