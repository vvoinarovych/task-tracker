package com.vvoinarovych.tasktrackerbackend.security.exception;

public class UserExistsException extends RuntimeException{
    public UserExistsException(String message) {
        super(message);
    }
}
