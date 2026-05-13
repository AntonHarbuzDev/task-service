package com.codemania.task_service.service;

import com.codemania.task_service.exception.EntityNotFoundException;
import com.codemania.task_service.model.Comment;
import com.codemania.task_service.model.Task;
import com.codemania.task_service.model.dto.CommentCreateDto;
import com.codemania.task_service.model.dto.CommentDto;
import com.codemania.task_service.model.dto.CommentUpdateDto;
import com.codemania.task_service.model.mapper.CommentMapper;
import com.codemania.task_service.repository.CommentRepository;
import com.codemania.task_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskRepository taskRepository;

    @Transactional
    public CommentDto create(CommentCreateDto dto) {
        Comment comment = commentMapper.toEntity(dto);
        Task task = taskRepository.getReferenceById(dto.getTaskId());
        comment.setTask(task);
        Comment commentCreated;
        try {
            commentCreated = commentRepository.save(comment);
            log.debug("Create comment - {} success", commentCreated);
            return commentMapper.toDto(commentCreated);
        } catch (DataIntegrityViolationException e) {
            log.warn("Failed to create comment for task id: {}", dto.getTaskId(), e);
            throw new EntityNotFoundException("Task id - " + dto.getTaskId() + " no found");
        }
    }

    @Transactional(readOnly = true)
    public CommentDto getById(Long id) {
        Comment commentLoad = loadById(id);
        return commentMapper.toDto(commentLoad);
    }

    @Transactional
    public CommentDto update(CommentUpdateDto dto) {
        Comment commentLoad = loadById(dto.getId());
        commentMapper.updateCommentFromDto(dto, commentLoad);
        Comment commentSaved = commentRepository.save(commentLoad);
        log.debug("Update comment - {} success ", commentSaved);
        return commentMapper.toDto(commentSaved);
    }

    @Transactional
    public void deleteById(Long id) {
        int deletedCount = commentRepository.deleteCommentById(id);
        if (deletedCount == 0) {
            log.debug("Comment with id {} no found", id);
            throw new EntityNotFoundException("Comment with id - " + id + " not found");
        }
        log.debug("Comment with id {} was deleted successfully", id);
    }

    private Comment loadById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment with id - " + id + " no found"));

    }
}
