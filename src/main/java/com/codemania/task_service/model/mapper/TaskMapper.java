package com.codemania.task_service.model.mapper;

import com.codemania.task_service.model.Comment;
import com.codemania.task_service.model.Task;
import com.codemania.task_service.model.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskCreateDto createDto);

    @Mapping(target = "comments", ignore = true)
    void updateTaskFromDto(TaskUpdateDto dto, @MappingTarget Task task);

    @Mapping(source = "comments", target = "commentIds")
    TaskDto toDto(Task task);

    void updateTaskFromDto(TaskStatusUpdateDto dto, @MappingTarget Task task);

    TaskOutbox toTaskOutbox(Task task);

    default List<Long> mapCommentsToIds(List<Comment> comments) {
        if(comments == null) {
            return new ArrayList<>();
        }
        return comments.stream().map(Comment::getId).toList();
    }

}
