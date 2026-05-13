package com.codemania.task_service.model.dto;

import com.codemania.task_service.model.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusUpdateDto {

    private Long id;

    @NotNull(message = "need to set status")
    private Status status;
}
