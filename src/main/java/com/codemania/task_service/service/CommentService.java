package com.codemania.task_service.service;

import com.codemania.task_service.model.dto.CommentCreateDto;
import com.codemania.task_service.model.dto.CommentDto;
import com.codemania.task_service.model.dto.CommentUpdateDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {

    CommentDto create(CommentCreateDto dto);

    @Transactional(readOnly = true)
    CommentDto getById(Long id);

    @Transactional(readOnly = true)
    List<CommentDto> getByTaskId(Long taskId);

    CommentDto update(CommentUpdateDto dto);

    void deleteById(Long id);
}
