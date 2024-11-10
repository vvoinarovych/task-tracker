package com.vvoinarovych.tasktrackerbackend.exception;

import com.vvoinarovych.tasktrackerbackend.dto.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ExceptionResponse> handleException(UserExistsException exp) {
        return ResponseEntity
                .status(CONFLICT)
                .body(new ExceptionResponse(exp.getMessage()));
    }
}
