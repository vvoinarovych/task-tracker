package com.vvoinarovych.tasktrackerbackend.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ExceptionResponse(String error) {
}
