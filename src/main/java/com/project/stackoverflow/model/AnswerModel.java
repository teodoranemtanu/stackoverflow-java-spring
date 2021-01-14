package com.project.stackoverflow.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class AnswerModel {
    private String id;
    private String body;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String questionId;
    private String userId;
    private int voteCount;

    public AnswerModel() {
        this.id = UUID.randomUUID().toString();
    }

    public AnswerModel(String id, String body, LocalDateTime created_at, String questionId, String userId) {
        this.id = id;
        this.body = body;
        this.createdAt = created_at;
        this.questionId = questionId;
        this.userId = userId;
    }

    public AnswerModel(String id, String body, LocalDateTime createdAt, String questionId, String userId, int voteCount) {
        this.id = id;
        this.body = body;
        this.createdAt = createdAt;
        this.questionId = questionId;
        this.userId = userId;
        this.voteCount = voteCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.createdAt = created_at;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}

