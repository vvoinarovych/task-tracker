package com.vvoinarovych.tasktrackerbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
public class ExceptionResponse {

    private String error;
}