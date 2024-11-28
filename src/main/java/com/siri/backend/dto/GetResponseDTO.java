package com.siri.backend.dto;

import java.util.Set;

public class GetResponseDTO {
    private final String id;
    private final String status;
    private final Set<String> urls;

    public GetResponseDTO(String id, String status, Set<String> urls) {
        this.id = id;
        this.status = status;
        this.urls = urls;
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
