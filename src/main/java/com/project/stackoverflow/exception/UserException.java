package com.project.stackoverflow.exception;

public class UserException extends RuntimeException {
    public UserException() {
        super("User operation could not be completed");
    }
}
