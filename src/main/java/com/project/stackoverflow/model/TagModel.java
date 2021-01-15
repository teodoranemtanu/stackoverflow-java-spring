package com.project.stackoverflow.model;

import java.util.UUID;

public class TagModel {
    private String id;
    private String questionId;
    private String title;

    public TagModel() {
        this.id = UUID.randomUUID().toString();
    }

    public TagModel(String id, String title, String questionId) {
        this.id = id;
        this.title = title;
        this.questionId = questionId;
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
}
