package com.codemania.task_service.controller;

import com.codemania.task_service.model.dto.CommentDto;
import com.codemania.task_service.model.dto.TaskCreateDto;
import com.codemania.task_service.model.dto.TaskDto;
import com.codemania.task_service.model.dto.TaskUpdateDto;
import com.codemania.task_service.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@RequestBody @Valid TaskCreateDto dto) {
        log.debug("Get request for task create: TaskCreateDto - {}", dto);
        return taskService.create(dto);
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        log.debug("Get request for task getById: id - {}", id);
        return taskService.getById(id);
    }

    @GetMapping("/{id}/comments")
    public List<CommentDto> getCommentsByTaskId(@PathVariable Long id) {
        log.debug("Get request for task getById: id - {}", id);
        return taskService.getCommentsByTaskId(id);
    }

    @PutMapping
    public TaskDto update(@RequestBody @Valid TaskUpdateDto dto) {
        log.debug("Get request for task update: TaskUpdateDto - {}", dto);
        return taskService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        log.debug("Get request for task deleteById: id - {}", id);
        taskService.deleteById(id);
    }
}
