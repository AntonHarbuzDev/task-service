package com.codemania.task_service.model.dto;

import com.codemania.task_service.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskKafka {

    private Long id;
    private String title;
    private Status status;
}
