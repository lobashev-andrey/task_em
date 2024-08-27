package com.example.task_em.service;

import com.example.task_em.entity.Status;
import com.example.task_em.entity.Task;
import com.example.task_em.entity.User;
import com.example.task_em.filter.TaskFilter;
import com.example.task_em.filter.TaskSpecification;
import com.example.task_em.repository.TaskRepository;
import com.example.task_em.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }



    @Override
    public List<Task> findAll() {
        log.info("findAll() is called");

        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        log.info(MessageFormat.format("findById()) is called with ID = {0}", id));

        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Задача с ID = {0} не найдена", id)));
    }

    @Override
    public List<Task> filterBy(TaskFilter filter) {
        return taskRepository.findAll(
                TaskSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize()))
                .getContent();
    }

//    @Override
//    public List<Task> findByAuthor(User author) {
//        log.info(MessageFormat.format("findByAuthorId() is called with userId = {0}", author.getId()));
//
//        return taskRepository.findByAuthor(author);
//    }
//
//    @Override
//    public List<Task> findByPerformer(User performer) {
//        log.info(MessageFormat.format("findByPerformerId()) is called with ID = {0}", performer.getId()));
//
//        return taskRepository.findByPerformer(performer);
//    }

    @Override
    public Task create(Task task) {
        log.info("create() is called");

        return taskRepository.save(task);
    }


    @Override
    public Task update(Task task) {
        log.info("update() is called");

        Task existedTask = findById(task.getId());
        BeanUtils.copyNonNullProperties(task, existedTask);

        return taskRepository.save(existedTask);
    }

    @Override
    public Task changeStatus(Long id, String newStatus) {
        log.info(MessageFormat.format("changeStatus() is called with id = {0} and newStatus = {1}", id, newStatus));

        Task existedTask = findById(id);
        existedTask.setStatus(Status.valueOf(newStatus));

        return taskRepository.save(existedTask);
    }

    @Override
    public void deleteById(Long id) {
        log.info(MessageFormat.format("deleteById() is called with id = {0}", id));

        taskRepository.delete(findById(id));
    }
}
