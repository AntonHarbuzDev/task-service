package com.codemania.task_service.model.dto;

import com.codemania.task_service.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateDto {

    @NotBlank(message = "title cannot be empty")
    @Size(max = 100, message = "title max 100 symbol")
    private String title;

    private String description;

    @NotNull(message = "need to set priority")
    private Priority priority;
}
