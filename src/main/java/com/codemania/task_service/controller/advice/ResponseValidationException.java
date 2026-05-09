package com.codemania.task_service.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public final class ResponseValidationException {

    private String message;
    private List<ResponceException> errors;
}
