package com.codemania.task_service.model.mapper;

import com.codemania.task_service.model.Comment;
import com.codemania.task_service.model.Task;
import com.codemania.task_service.model.dto.CommentCreateDto;
import com.codemania.task_service.model.dto.CommentDto;
import com.codemania.task_service.model.dto.CommentUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toEntity(CommentCreateDto dto);

    @Mapping(target = "task", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateCommentFromDto(CommentUpdateDto dto, @MappingTarget Comment comment);

    @Mapping(source = "task", target = "taskId")
    CommentDto toDto(Comment comment);

    default Long taskToId(Task task) {
        return task != null ? task.getId() : null;
    }
}
