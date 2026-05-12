package com.codemania.task_service.model.dto;

import com.codemania.task_service.model.Priority;
import com.codemania.task_service.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Instant createdAt;
    private Instant updatedAt;
    private List<Long> commentIds;
}
