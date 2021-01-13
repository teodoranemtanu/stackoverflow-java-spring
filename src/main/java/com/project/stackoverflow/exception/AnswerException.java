package com.project.stackoverflow.exception;

public class AnswerException extends RuntimeException{
    public AnswerException() {
        super("Answer operation could not be completed");
    }
}
