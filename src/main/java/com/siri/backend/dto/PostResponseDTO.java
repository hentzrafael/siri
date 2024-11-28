package com.siri.backend.dto;

public class PostResponseDTO {
    private final String id;

    public PostResponseDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
