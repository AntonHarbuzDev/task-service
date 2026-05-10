package com.codemania.task_service.repository;

import com.codemania.task_service.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTaskId(Long taskId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id = :id")
    int deleteCommentById(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.taskId = :taskId")
    int deleteCommentsByTaskId(@Param("taskId") Long taskId);
}
