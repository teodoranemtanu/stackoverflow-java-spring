package com.project.stackoverflow.exception;

public class QuestionException extends RuntimeException {
    public enum QuestionErrors {
        QUESTION_NOT_FOUND,
        QUESTION_ALREADY_EXISTS,
        QUESTION_COULD_NOT_BE_SAVED,
        QUESTION_COULD_NOT_BE_REMOVED
    }

    private QuestionErrors error;

    private QuestionException(QuestionErrors error) {
        this.error = error;
    }

    public QuestionErrors getError() {
        return error;
    }

    @Override
    public String toString() {
        return error.name().toUpperCase();
    }

    public static QuestionException questionNotFound() {
        return new QuestionException(QuestionErrors.QUESTION_NOT_FOUND);
    }

    public static QuestionException questionAlreadyExists() {
        return new QuestionException(QuestionErrors.QUESTION_ALREADY_EXISTS);
    }

    public static QuestionException questionCouldNotBeSaved() {
        return new QuestionException(QuestionErrors.QUESTION_COULD_NOT_BE_SAVED);
    }

    public static QuestionException questionCouldNotBeRemoved() {
        return new QuestionException(QuestionErrors.QUESTION_COULD_NOT_BE_REMOVED);
    }
}
