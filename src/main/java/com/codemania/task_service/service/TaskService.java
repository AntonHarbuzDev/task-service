package com.codemania.task_service.service;

import com.codemania.task_service.exception.EntityNotFoundException;
import com.codemania.task_service.model.Status;
import com.codemania.task_service.model.Task;
import com.codemania.task_service.model.dto.*;
import com.codemania.task_service.model.mapper.CommentMapper;
import com.codemania.task_service.model.mapper.TaskMapper;
import com.codemania.task_service.repository.TaskRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CommentMapper commentMapper;
    private final OutboxMessageService outboxMessageService;
    private final ObjectMapper objectMapper;

    @Transactional
    public TaskDto create(TaskCreateDto taskCreateDto) {
        Task task = taskMapper.toEntity(taskCreateDto);
        task.setStatus(Status.NEW);
        Task taskCreated = taskRepository.save(task);
        log.debug("Create task - {} success", taskCreated);
        return taskMapper.toDto(taskCreated);
    }

    @Transactional(readOnly = true)
    public TaskDto getById(Long id) { //добавить редис
        Task taskLoad = loadById(id);
        return taskMapper.toDto(taskLoad);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByTaskId(Long id) {
        Task taskLoad = loadById(id);
        return taskLoad.getComments().stream().map(commentMapper::toDto).toList();
    }

    @Transactional
    public TaskDto update(TaskStatusUpdateDto dto) {
        Task taskLoad = loadById(dto.getId());
        taskMapper.updateTaskFromDto(dto, taskLoad);
        Task taskSaved = taskRepository.save(taskLoad);
        log.debug("Update status from task - {} success", taskSaved);

        TaskOutbox taskOutbox = taskMapper.toTaskOutbox(taskSaved);
        String payloadJson;
        try {
            payloadJson = objectMapper.writeValueAsString(taskOutbox);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации TaskOutbox в JSON: " + e.getMessage(), e);
        }

        OutboxMessageCreateDto outboxMessageCreateDto = new OutboxMessageCreateDto(
                taskSaved.getClass().getName(),
                payloadJson
        );
        outboxMessageService.create(outboxMessageCreateDto);
        return taskMapper.toDto(taskSaved);
    }

    @Transactional
    public TaskDto update(TaskUpdateDto taskUpdateDto) {
        Task taskLoad = loadById(taskUpdateDto.getId());
        taskMapper.updateTaskFromDto(taskUpdateDto, taskLoad);
        Task taskSaved = taskRepository.save(taskLoad);
        log.debug("Update task - {} success ", taskSaved);
        return taskMapper.toDto(taskSaved);
    }

    @Transactional
    public void deleteById(Long id) {
        int deletedCount = taskRepository.deleteTaskById(id);
        if (deletedCount == 0) {
            log.debug("Task id - {} no found", id);
            throw new EntityNotFoundException("Task with id - " + id + " no found");
        }
        log.debug("Task with id {} was deleted successfully", id);
    }

    private Task loadById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task with id - " + id + " no found"));
    }
}
