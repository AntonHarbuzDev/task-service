package com.codemania.task_service.model.dto;

import com.codemania.task_service.model.Priority;
import com.codemania.task_service.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateDto {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
}
