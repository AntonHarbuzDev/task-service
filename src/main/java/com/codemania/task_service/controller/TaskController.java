package com.codemania.task_service.controller;

import com.codemania.task_service.model.dto.TaskCreateDto;
import com.codemania.task_service.model.dto.TaskDto;
import com.codemania.task_service.model.dto.TaskUpdateDto;
import com.codemania.task_service.service.imp.TaskServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskServiceImp taskServiceImp;

    @PostMapping
    public ResponseEntity<TaskDto> create(@RequestBody TaskCreateDto dto) {
        log.info("Get request for task create: TaskCreateDto - {}", dto);
        TaskDto taskDto = taskServiceImp.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable Long id) {
        log.info("Get request for task getById: id - {}", id);
        TaskDto taskDto = taskServiceImp.getById(id);
        return ResponseEntity.ok().body(taskDto);
    }

    @PutMapping
    public ResponseEntity<TaskDto> update(@RequestBody TaskUpdateDto dto) {
        log.info("Get request for task update: TaskUpdateDto - {}", dto);
        TaskDto taskDto = taskServiceImp.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Get request for task deleteById: id - {}", id);
        taskServiceImp.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
