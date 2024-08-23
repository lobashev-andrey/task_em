package com.example.task_em.controller;

import com.example.task_em.dto.TaskRequest;
import com.example.task_em.dto.TaskResponse;
import com.example.task_em.dto.TaskResponseList;
import com.example.task_em.mapper.TaskMapper;
import com.example.task_em.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;


    @GetMapping
    public TaskResponseList getAllTasks() {
        return taskMapper.taskListToResponseList(
                taskService.findAll());
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskMapper.taskToResponse(
                taskService.findById(id));
    }

    @GetMapping("/author/{userId}")
    public TaskResponseList getAllByAuthor(@PathVariable Long userId) {
        return taskMapper.taskListToResponseList(
                taskService.findByAuthorId(userId));
    }

    @GetMapping("/performer/{userId}")
    public TaskResponseList getAllByPerformer(@PathVariable Long userId) {
        return taskMapper.taskListToResponseList(
                taskService.findByPerformerId(userId));
    }

    @PostMapping
    public TaskResponse create(@RequestBody TaskRequest request) {
        return taskMapper.taskToResponse(
                taskService.create(
                        taskMapper.requestToTask(request)));
    }

    @PutMapping("/{id}")
    public TaskResponse update(@RequestBody TaskRequest request, @PathVariable Long id) {
        return taskMapper.taskToResponse(
                taskService.update(
                        taskMapper.requestToTask(request, id)));
    }

    @PatchMapping("/{id}/{newStatus}")
    public TaskResponse changeStatus(@PathVariable Long id, @PathVariable String newStatus) {
        return taskMapper.taskToResponse(
                taskService.changeStatus(id, newStatus));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
