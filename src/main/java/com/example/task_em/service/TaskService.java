package com.example.task_em.service;

import com.example.task_em.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    Task findById(Long id);

    List<Task> findByAuthorId(Long userId);

    List<Task> findByPerformerId(Long userId);

    Task create(Task task);

    Task update(Task task);

    Task changeStatus(Long id, String newStatus);

    void deleteById(Long id);


}
