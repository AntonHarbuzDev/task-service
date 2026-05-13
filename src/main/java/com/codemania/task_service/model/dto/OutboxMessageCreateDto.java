package com.codemania.task_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutboxMessageCreateDto {

    private String entityType;
    private String payload;
}
