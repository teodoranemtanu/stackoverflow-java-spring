package com.project.stackoverflow.exception;

public class VoteException extends RuntimeException{
    public VoteException() {
        super("Vote operation could not be completed.");
    }
}
