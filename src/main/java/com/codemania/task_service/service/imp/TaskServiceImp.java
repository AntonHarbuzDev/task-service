package com.codemania.task_service.service.imp;

import com.codemania.task_service.model.Task;
import com.codemania.task_service.model.dto.TaskCreateDto;
import com.codemania.task_service.model.dto.TaskDto;
import com.codemania.task_service.model.dto.TaskUpdateDto;
import com.codemania.task_service.model.mapper.TaskMapper;
import com.codemania.task_service.repository.TaskRepository;
import com.codemania.task_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImp implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto create(TaskCreateDto taskCreateDto) {
        Task task = taskMapper.toEntity(taskCreateDto);
        Task taskCreated = taskRepository.save(task);
        log.info("Create task - {} success", taskCreated);
        return taskMapper.toDto(taskCreated);
    }

    @Transactional(readOnly = true)
    @Override
    public TaskDto getById(Long id) {
        Task taskLoad = loadById(id);
        return taskMapper.toDto(taskLoad);
    }

    @Override
    public TaskDto update(TaskUpdateDto taskUpdateDto) {
        Task taskLoad = loadById(taskUpdateDto.getId());
        taskMapper.updateTaskFromDto(taskUpdateDto, taskLoad);
        Task taskSaved = taskRepository.save(taskLoad);
        log.info("Update task - {} success ", taskSaved);
        return taskMapper.toDto(taskSaved);
    }

    @Override
    public void deleteById(Long id) {
        loadById(id);
        taskRepository.deleteById(id);
        log.info("Delete task with id - {} success", id);
    }

    @Override
    public Task loadEntityById(Long id) {
        return loadById(id);
    }

    private Task loadById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task with id - " + id + " no found"));
    }
}
