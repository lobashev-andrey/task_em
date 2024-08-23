package com.example.task_em.service;

import com.example.task_em.entity.Status;
import com.example.task_em.entity.Task;
import com.example.task_em.repository.TaskRepository;
import com.example.task_em.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }



    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Задача с ID = {0} не найдена", id)));
    }

    @Override
    public List<Task> findByAuthorId(Long userId) {
        return taskRepository.findByAuthorId(userId);
    }

    @Override
    public List<Task> findByPerformerId(Long userId) {
        return taskRepository.findByPerformerId(userId);
    }

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }


    @Override
    public Task update(Task task) {
        Task existedTask = findById(task.getId());
        BeanUtils.copyNonNullProperties(task, existedTask);

        return taskRepository.save(existedTask);
    }

    @Override
    public Task changeStatus(Long id, String newStatus) {
        Task existedTask = findById(id);
        existedTask.setStatus(Status.valueOf(newStatus));

        return taskRepository.save(existedTask);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.delete(findById(id));
    }
}
