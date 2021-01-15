package com.project.stackoverflow.exception;

import java.util.Locale;

public class AnswerException extends RuntimeException {

    public enum AnswerErrors {
        ANSWER_NOT_FOUND,
        ANSWER_ALREADY_EXISTS,
        ANSWER_COULD_NOT_BE_SAVED,
        ANSWER_COULD_NOT_BE_REMOVED,
        ANSWER_ALREADY_VOTED_BY_THIS_USER,
        VOTE_COULD_NOT_BE_SAVED,
        VOTE_COULD_NOT_BE_REMOVED
    }

    private AnswerErrors error;

    private AnswerException(AnswerErrors error) {
        this.error = error;
    }

    public AnswerErrors getError() {
        return error;
    }

    @Override
    public String toString() {
        return error.name().toUpperCase();
    }

    public static AnswerException answerNotFound() {
        return new AnswerException(AnswerErrors.ANSWER_NOT_FOUND);
    }

    public static AnswerException answerAlreadyExists() {
        return new AnswerException(AnswerErrors.ANSWER_ALREADY_EXISTS);
    }

    public static AnswerException answerCouldNotBeSaved() {
        return new AnswerException(AnswerErrors.ANSWER_COULD_NOT_BE_SAVED);
    }

    public static AnswerException answerCouldNotBeRemoved() {
        return new AnswerException(AnswerErrors.ANSWER_COULD_NOT_BE_REMOVED);
    }

    public static AnswerException answerAlreadyVotedByThisUser() {
        return new AnswerException(AnswerErrors.ANSWER_ALREADY_VOTED_BY_THIS_USER);
    }

    public static AnswerException voteCouldNotBeSaved() {
        return new AnswerException(AnswerErrors.VOTE_COULD_NOT_BE_SAVED);
    }

    public static AnswerException voteCouldNotBeRemoved() {
        return new AnswerException(AnswerErrors.VOTE_COULD_NOT_BE_REMOVED);
    }
}
