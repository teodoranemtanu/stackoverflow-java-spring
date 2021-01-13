package com.project.stackoverflow.exception;

public class CommunityException extends RuntimeException {
    public CommunityException() {
        super("Community operation could not be completed");
    }
}