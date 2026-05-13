package com.codemania.task_service.model.dto;

import com.codemania.task_service.model.Priority;
import com.codemania.task_service.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateDto {

    private Long id;

    @NotBlank(message = "title cannot be empty")
    @Size(max = 100, message = "title max 100 symbol")
    private String title;

    private String description;

    @NotNull(message = "need to set status")
    private Status status;

    @NotNull(message = "need to set priority")
    private Priority priority;
}
