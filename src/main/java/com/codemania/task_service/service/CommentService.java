package com.codemania.task_service.service;

import com.codemania.task_service.model.Comment;
import com.codemania.task_service.model.dto.CommentCreateDto;
import com.codemania.task_service.model.dto.CommentDto;
import com.codemania.task_service.model.dto.CommentUpdateDto;
import com.codemania.task_service.model.mapper.CommentMapper;
import com.codemania.task_service.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskService taskService;

    @Transactional
    public CommentDto create(CommentCreateDto dto) {
        Comment comment = commentMapper.toEntity(dto);
        comment.setTask(taskService.loadEntityById(dto.getTaskId())); //как можно сделать лучше?
        Comment commentCreated = commentRepository.save(comment);
        log.debug("Create comment - {} success", commentCreated);
        return commentMapper.toDto(commentCreated);
    }

    @Transactional(readOnly = true)
    public CommentDto getById(Long id) {
        Comment commentLoad = loadById(id);
        return commentMapper.toDto(commentLoad);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getByTaskId(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        return comments.stream().map(commentMapper::toDto).toList();
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
        if  (deletedCount > 0) {
            log.debug("Delete comment with id - {} is success", id);
        } else {
            log.debug("Comment with id {} not found", id);
            throw new NoSuchElementException("Comment with id - " + id + " not found");
        }
    }

    private Comment loadById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Comment with id - " + id + " no found"));

    }
}
