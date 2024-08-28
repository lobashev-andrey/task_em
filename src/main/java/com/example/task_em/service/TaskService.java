package com.example.task_em.service;

import com.example.task_em.entity.Task;
import com.example.task_em.filter.TaskFilter;

import java.util.List;

public interface TaskService {

    Task findById(Long id);

    List<Task> filterBy(TaskFilter filter);

    Task create(Task task);

    Task update(Task task);

    Task changeStatus(Long id, String newStatus);

    void deleteById(Long id);


}
