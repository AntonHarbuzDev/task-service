package com.codemania.task_service.service.imp;

import com.codemania.task_service.model.Comment;
import com.codemania.task_service.model.dto.CommentCreateDto;
import com.codemania.task_service.model.dto.CommentDto;
import com.codemania.task_service.model.dto.CommentUpdateDto;
import com.codemania.task_service.model.mapper.CommentMapper;
import com.codemania.task_service.repository.CommentRepository;
import com.codemania.task_service.service.CommentService;
import com.codemania.task_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskService taskService;

    @Override
    public CommentDto create(CommentCreateDto dto) {
        Comment comment = commentMapper.toEntity(dto);
        comment.setTask(taskService.loadEntityById(dto.getTaskId())); //как можно сделать лучше?
        Comment commentCreated = commentRepository.save(comment);
        log.info("Create comment - {} success", commentCreated);
        return commentMapper.toDto(commentCreated);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto getById(Long id) {
        Comment commentLoad = loadById(id);
        return commentMapper.toDto(commentLoad);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getByTaskId(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        return comments.stream().map(commentMapper::toDto).toList();
    }

    @Override
    public CommentDto update(CommentUpdateDto dto) {
        Comment commentLoad = loadById(dto.getId());
        commentMapper.updateCommentFromDto(dto, commentLoad);
        Comment commentSaved = commentRepository.save(commentLoad);
        log.info("Update comment - {} success ", commentSaved);
        return commentMapper.toDto(commentSaved);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(loadById(id).getId());
        log.info("Delete comment with id - {} success", id);
    }

    private Comment loadById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Comment with id - " + id + " no found"));

    }
}
