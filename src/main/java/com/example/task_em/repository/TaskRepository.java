package com.example.task_em.repository;

import com.example.task_em.entity.Task;
import com.example.task_em.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    List<Task> findByAuthor(User author);

    List<Task> findByPerformer(User performer);
}
