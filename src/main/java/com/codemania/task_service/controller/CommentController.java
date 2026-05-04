package com.codemania.task_service.controller;

import com.codemania.task_service.model.dto.CommentCreateDto;
import com.codemania.task_service.model.dto.CommentDto;
import com.codemania.task_service.model.dto.CommentUpdateDto;
import com.codemania.task_service.service.imp.CommentServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImp commentServiceImp;

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentCreateDto dto) {
        CommentDto commentDto = commentServiceImp.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getById(@PathVariable Long id) {
        CommentDto commentDto = commentServiceImp.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @GetMapping("all/{taskId}")
    public ResponseEntity<List<CommentDto>> getAllByTaskId(@PathVariable Long taskId) {
        List<CommentDto> commentsDto = commentServiceImp.getByTaskId(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
    }

    @PutMapping
    public ResponseEntity<CommentDto> update(@RequestBody CommentUpdateDto dto) {
        CommentDto commentDto = commentServiceImp.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        commentServiceImp.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

