package com.example.task_em.controller;

import com.example.task_em.dto.TaskRequest;
import com.example.task_em.dto.TaskResponse;
import com.example.task_em.dto.TaskResponseList;
import com.example.task_em.entity.Task;
import com.example.task_em.mapper.TaskMapper;
import com.example.task_em.service.TaskService;
import com.example.task_em.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final UserService userService;


    @GetMapping
    public TaskResponseList getAllTasks() {
        log.info("getAllTasks()  is called");

        return taskMapper.taskListToResponseList(
                taskService.findAll());
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        log.info("getTaskById() is called");

        return taskMapper.taskToResponse(
                taskService.findById(id));
    }

    @GetMapping("/author/{userId}")
    public TaskResponseList getAllByAuthor(@PathVariable Long userId) {
        log.info("getAllByAuthor() is called");

        return taskMapper.taskListToResponseList(
                taskService.findByAuthor(userService.findById(userId)));
    }

    @GetMapping("/performer/{userId}")
    public TaskResponseList getAllByPerformer(@PathVariable Long userId) {
        log.info("getAllByPerformer() is called");

        return taskMapper.taskListToResponseList(
                taskService.findByPerformer(userService.findById(userId)));
    }

    @PostMapping
    public TaskResponse create(@Valid @RequestBody TaskRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("create() is called");

        Task task = taskMapper.requestToTask(request);
        task.setAuthor(userService.findByUsername(userDetails.getUsername()));

        return taskMapper.taskToResponse(
                taskService.create(task));
    }

    @PutMapping("/{id}")
    public TaskResponse update(@Valid @RequestBody TaskRequest request, @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) throws IllegalAccessException {
        log.info("update() is called");

        if(!isAuthor(id, userDetails)) {
            throw new IllegalAccessException("Внести изменения в задачу может только ее автор");
        };

        return taskMapper.taskToResponse(
                taskService.update(
                        taskMapper.requestToTask(request, id)));
    }

    @PatchMapping("/{id}/{newStatus}")
    public TaskResponse changeStatus(@PathVariable Long id, @PathVariable String newStatus, @AuthenticationPrincipal UserDetails userDetails) throws IllegalAccessException {
        log.info("changeStatus() is called");

        if(!isAuthor(id, userDetails) && !isPerformer(id, userDetails)) {
            throw new IllegalAccessException("Изменить статус задачи может только ее автор или исполнитель");
        };

        return taskMapper.taskToResponse(
                taskService.changeStatus(id, newStatus));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) throws IllegalAccessException {
        log.info("delete() is called");

        if(!isAuthor(id, userDetails)) {
            throw new IllegalAccessException("Удалить задачу может только ее автор");
        };

        taskService.deleteById(id);
    }



    private boolean isAuthor(Long id, UserDetails userDetails){
        log.info("isAuthor() is called");

        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        Long authorId = taskService.findById(id).getAuthor().getId();

        return Objects.equals(userId, authorId);
    }

    private boolean isPerformer(Long id, UserDetails userDetails){
        log.info("isPerformer() is called");

        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        Long performerId = taskService.findById(id).getPerformer().getId();

        return Objects.equals(userId, performerId);
    }
}
