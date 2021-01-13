package com.project.stackoverflow.model;

import java.util.UUID;

public class VoteModel {
    private String id;
    private String userId;
    private String answerId;

    public VoteModel() {
        this.id = UUID.randomUUID().toString();
    }

    public VoteModel(String id, String userId, String answerId) {
        this.id = id;
        this.userId = userId;
        this.answerId = answerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}
