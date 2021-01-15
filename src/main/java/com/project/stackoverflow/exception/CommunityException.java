package com.project.stackoverflow.exception;

public class CommunityException extends RuntimeException {
    public enum CommunityErrors {
        COMMUNITY_NOT_FOUND,
        COMMUNITY_COULD_NOT_BE_SAVED,
        COMMUNITY_ALREADY_EXISTS,
        COMMUNITY_COULD_NOT_BE_REMOVED,
        USER_ALREADY_IN_COMMUNITY,
        USER_COULD_NOT_JOIN_COMMUNITY,
        USER_COULD_NOT_LEAVE_COMMUNITY,
        COMMUNITY_HAS_NO_USERS,
        COMMUNITY_HAS_NO_QUESTIONS
    }

    private CommunityErrors error;

    private CommunityException(CommunityErrors error) {
        this.error = error;
    }

    public CommunityErrors getError() {
        return error;
    }

    @Override
    public String toString() {
        return error.name().toUpperCase();
    }

    public static CommunityException communityNotFound() {
        return new CommunityException(CommunityErrors.COMMUNITY_NOT_FOUND);
    }

    public static CommunityException communityCouldNotBeSaved() {
        return new CommunityException(CommunityErrors.COMMUNITY_COULD_NOT_BE_SAVED);
    }

    public static CommunityException communityAlreadyExists() {
        return new CommunityException(CommunityErrors.COMMUNITY_ALREADY_EXISTS);
    }

    public static CommunityException communityCouldNotBeRemoved(){
        return new CommunityException(CommunityErrors.COMMUNITY_COULD_NOT_BE_REMOVED);
    }

    public static CommunityException userAlreadyInCommunity() {
        return new CommunityException(CommunityErrors.USER_ALREADY_IN_COMMUNITY);
    }

    public static CommunityException userCouldNotJoinCommunity() {
        return new CommunityException(CommunityErrors.USER_COULD_NOT_JOIN_COMMUNITY);
    }

    public static CommunityException userCouldNotLeaveCommunity() {
        return new CommunityException(CommunityErrors.USER_COULD_NOT_LEAVE_COMMUNITY);
    }

    public static CommunityException communityHasNoUsers() {
        return new CommunityException(CommunityErrors.COMMUNITY_HAS_NO_USERS);
    }

    public static CommunityException communityHasNoQuestions() {
        return new CommunityException(CommunityErrors.COMMUNITY_HAS_NO_QUESTIONS);
    }
}