package com.example.task_em.service;

import com.example.task_em.entity.Task;
import com.example.task_em.entity.User;
import com.example.task_em.filter.TaskFilter;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    Task findById(Long id);

    List<Task> filterBy(TaskFilter filter);

//    List<Task> findByAuthor(User author);
//
//    List<Task> findByPerformer(User performer);

    Task create(Task task);

    Task update(Task task);

    Task changeStatus(Long id, String newStatus);

    void deleteById(Long id);


}
