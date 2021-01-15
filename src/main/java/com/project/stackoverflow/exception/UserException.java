package com.project.stackoverflow.exception;

public class UserException extends RuntimeException {
    public enum UserErrors {
        USERS_NOT_FOUND,
        USER_COULD_NOT_BE_SAVED,
        USER_COULD_NOT_BE_REMOVED,
        USER_ALREADY_EXISTS
    }

    private UserException.UserErrors error;

    private UserException(UserException.UserErrors error) {
        this.error = error;
    }

    public UserException.UserErrors getError() {
        return error;
    }

    @Override
    public String toString() {
        return error.name().toUpperCase();
    }

    public static UserException usersNotFound() {
        return new UserException(UserErrors.USERS_NOT_FOUND);
    }

    public static UserException userCouldNotBeSaved() {
        return new UserException(UserErrors.USER_COULD_NOT_BE_SAVED);
    }

    public static UserException userCouldNotBeRemoved(){
        return new UserException(UserErrors.USER_COULD_NOT_BE_REMOVED);
    }

    public static UserException userAlreadyExists() {
        return new UserException(UserErrors.USER_ALREADY_EXISTS);
    }
}
