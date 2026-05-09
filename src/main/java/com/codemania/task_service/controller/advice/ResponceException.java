package com.codemania.task_service.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ResponceException {

    private String message;
    private Object cause;
}
