package com.project.stackoverflow.exception;

public class TagException extends RuntimeException {
    public enum TagErrors {
        TAG_ALREADY_EXISTS,
        TAG_NOT_FOUND,
        TAG_COULD_NOT_BE_SAVED,
        TAG_COULD_NOT_BE_REMOVED
    }

    private TagException.TagErrors error;

    private TagException(TagException.TagErrors error) {
        this.error = error;
    }

    public TagException.TagErrors getError() {
        return error;
    }

    @Override
    public String toString() {
        return error.name().toUpperCase();
    }

    public static TagException tagAlreadyExists() {
        return new TagException(TagErrors.TAG_ALREADY_EXISTS);
    }

    public static TagException tagNotFounds() {
        return new TagException(TagErrors.TAG_NOT_FOUND);
    }

    public static TagException tagCouldNotBeSaved() {
        return new TagException(TagErrors.TAG_COULD_NOT_BE_SAVED);
    }

    public static TagException tagCouldNotBeRemoved() {
        return new TagException(TagErrors.TAG_COULD_NOT_BE_REMOVED);
    }
}
