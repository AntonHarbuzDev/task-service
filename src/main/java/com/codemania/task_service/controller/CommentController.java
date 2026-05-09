package com.codemania.task_service.controller;

import com.codemania.task_service.model.dto.CommentCreateDto;
import com.codemania.task_service.model.dto.CommentDto;
import com.codemania.task_service.model.dto.CommentUpdateDto;
import com.codemania.task_service.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@RequestBody @Valid CommentCreateDto dto) {
        return commentService.create(dto);
    }

    @GetMapping("/{id}")
    public CommentDto getById(@PathVariable Long id) {
        return commentService.getById(id);
    }

    @GetMapping("all/{taskId}")
    public List<CommentDto> getAllByTaskId(@PathVariable Long taskId) {
        return commentService.getByTaskId(taskId);
    }

    @PutMapping
    public CommentDto update(@RequestBody @Valid CommentUpdateDto dto) {
        return commentService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
    }
}

