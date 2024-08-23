package com.example.task_em.repository;

import com.example.task_em.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAuthorId(Long authorId);

    List<Task> findByPerformerId(Long performerId);
}
