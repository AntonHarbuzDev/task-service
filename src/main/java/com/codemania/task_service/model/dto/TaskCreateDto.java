package com.codemania.task_service.model.dto;

import com.codemania.task_service.model.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateDto {

    private String title;
    private String description;
    private Priority priority;
}
