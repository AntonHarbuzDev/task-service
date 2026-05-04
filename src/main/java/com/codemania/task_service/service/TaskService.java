package com.codemania.task_service.service;

import com.codemania.task_service.model.Task;
import com.codemania.task_service.model.dto.TaskCreateDto;
import com.codemania.task_service.model.dto.TaskDto;
import com.codemania.task_service.model.dto.TaskUpdateDto;
import org.springframework.transaction.annotation.Transactional;

public interface TaskService {

    TaskDto create(TaskCreateDto dto);

    @Transactional(readOnly = true)
    TaskDto getById(Long id);

    TaskDto update(TaskUpdateDto taskUpdateDto);

    void deleteById(Long id);

    Task loadEntityById(Long id);
}
