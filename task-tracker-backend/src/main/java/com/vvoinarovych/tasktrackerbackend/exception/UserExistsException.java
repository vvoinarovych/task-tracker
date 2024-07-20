package com.vvoinarovych.tasktrackerbackend.exception;

public class UserExistsException extends RuntimeException{
    public UserExistsException(String message) {
        super(message);
    }
}
