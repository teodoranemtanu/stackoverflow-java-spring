package com.project.stackoverflow.exception;

public class QuestionException extends RuntimeException {
    public QuestionException() {
        super("Question operation could not be completed");
    }
}
