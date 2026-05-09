package com.codemania.task_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDto {

    @NotNull(message = "need to set taskId")
    private Long taskId;

    @NotBlank(message = "content cannot be empty")
    private String content;
}
