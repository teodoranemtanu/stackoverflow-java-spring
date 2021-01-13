package com.project.stackoverflow.model;

import java.util.UUID;

public class TagModel {
    private String id;
    private String questionId;
    private String title;
    private String communityId;

    public TagModel() {
        this.id = UUID.randomUUID().toString();
    }

    public TagModel(String id, String title, String questionId, String communityId) {
        this.id = id;
        this.title = title;
        this.questionId = questionId;
        this.communityId = communityId;
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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
}
