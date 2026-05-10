package com.codemania.task_service.service;

import com.codemania.task_service.model.Task;
import com.codemania.task_service.model.dto.TaskCreateDto;
import com.codemania.task_service.model.dto.TaskDto;
import com.codemania.task_service.model.dto.TaskUpdateDto;
import com.codemania.task_service.model.mapper.TaskMapper;
import com.codemania.task_service.repository.CommentRepository;
import com.codemania.task_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CommentRepository commentRepository; // так себе решение

    @Transactional
    public TaskDto create(TaskCreateDto taskCreateDto) {
        Task task = taskMapper.toEntity(taskCreateDto);
        Task taskCreated = taskRepository.save(task);
        log.debug("Create task - {} success", taskCreated);
        return taskMapper.toDto(taskCreated);
    }

    @Transactional(readOnly = true)
    public TaskDto getById(Long id) {
        Task taskLoad = loadById(id);
        return taskMapper.toDto(taskLoad);
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
        int deletedCommentsCount = commentRepository.deleteCommentsByTaskId(id);
        int deletedCount = taskRepository.deleteTaskById(id);
        if (deletedCount > 0) {
            log.debug("Deleted - {} comments", deletedCommentsCount);
            log.debug("Delete task with id - {} success", id);
        } else {
            log.debug("Task id - {} no found", id);
            throw new NoSuchElementException("Task with id - " + id + " no found");
        }
    }

    @Transactional
    public Task loadEntityById(Long id) {
        return loadById(id);
    }

    private Task loadById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task with id - " + id + " no found"));
    }
}
