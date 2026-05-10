package com.codemania.task_service.repository;

import com.codemania.task_service.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query("DELETE FROM Task t WHERE t.id = :id")
    int deleteTaskById(@Param("id") Long id);
}
