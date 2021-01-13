package com.project.stackoverflow.exception;

public class TagException extends RuntimeException {
    public TagException() {
        super("Tag operation could not be completed");
    }
}
