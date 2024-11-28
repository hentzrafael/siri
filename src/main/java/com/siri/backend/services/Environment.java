package com.siri.backend.services;

public class Environment {
    public String getBaseUrl() throws IllegalArgumentException {
        String baseUrl = System.getenv("BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalArgumentException("BASE_URL environment variable is not set");
        }
        return baseUrl;
    }
}
