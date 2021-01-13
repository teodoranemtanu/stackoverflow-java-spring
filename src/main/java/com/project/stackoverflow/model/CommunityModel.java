package com.project.stackoverflow.model;

import java.util.UUID;

public class CommunityModel {
    private String id;
    private String title;

    public CommunityModel() {
        this.id = UUID.randomUUID().toString();
    }

    public CommunityModel(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
