package com.example.task_em.controller;

import com.example.task_em.dto.TaskRequest;
import com.example.task_em.dto.TaskResponse;
import com.example.task_em.dto.TaskResponseList;
import com.example.task_em.entity.Task;
import com.example.task_em.filter.TaskFilter;
import com.example.task_em.mapper.TaskMapper;
import com.example.task_em.service.TaskService;
import com.example.task_em.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/task")
@RequiredArgsConstructor
@Tags({
        @Tag(name = "Task controller", description = "Task controller version 1.0") ,
        @Tag(name = "task")
})

public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final UserService userService;


    @Operation(
            summary = "Get tasks",
            description = "Get all tasks (first page by default)",
            tags = "filter"
    )
    @GetMapping
    public TaskResponseList findAll(TaskFilter filter) {
        log.info("getAllTasks()  is called");

        return filterBy(filter);
    }

    @Tag(name = "id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "get task by id",
                    content = {
                            @Content(schema = @Schema(implementation = TaskResponse.class),
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "task not found",
                    content = {
                            @Content(schema = @Schema(implementation = String.class),
                            mediaType = "application/json")
                    })
    })
    @GetMapping("/{id}")
    public TaskResponse findById(@PathVariable Long id) {
        log.info("getTaskById() is called");

        return taskMapper.taskToResponse(
                taskService.findById(id));
    }


    @Operation(
            summary = "Get tasks by filter",
            description = "Get all tasks by filter",
            tags = {"filter"}
    )
    @GetMapping("/filter")
    public TaskResponseList filterBy(TaskFilter filter) {
        log.info("filterBy() is called");

        return taskMapper.taskListToResponseList(
                taskService.filterBy(filter));
    }



    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "create task",
                    content = {
                            @Content(schema = @Schema(implementation = TaskResponse.class),
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "some parameter is not found in DB",
                    content = {
                            @Content(schema = @Schema(implementation = String.class),
                                    mediaType = "string")
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "some parameter is not valid",
                    content = {
                            @Content(schema = @Schema(implementation = String.class),
                                    mediaType = "string")
                    })
    })
    @PostMapping
    public TaskResponse create(@Valid @RequestBody TaskRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("create() is called");

        Task task = taskMapper.requestToTask(request);
        task.setAuthor(userService.findByUsername(userDetails.getUsername()));

        return taskMapper.taskToResponse(
                taskService.create(task));
    }

    @Tag(name = "id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "update task",
                    content = {
                            @Content(schema = @Schema(implementation = TaskResponse.class),
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "some parameter is not found in DB",
                    content = {
                            @Content(schema = @Schema(implementation = String.class),
                                    mediaType = "string")
                    }),
            @ApiResponse(
                    responseCode = "400", description = "some parameter is not valid",
                    content = {
                            @Content(schema = @Schema(implementation = String.class),
                                    mediaType = "string")
                    })
    })
    @PutMapping("/{id}")
    public TaskResponse update(@Valid @RequestBody TaskRequest request, @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) throws IllegalAccessException {
        log.info("update() is called");

        if(isNotAuthor(id, userDetails)) {
            throw new IllegalAccessException("Внести изменения в задачу может только ее автор");
        }

        return taskMapper.taskToResponse(
                taskService.update(
                        taskMapper.requestToTask(request, id)));
    }

    @Tag(name = "id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "change status",
                    content = {
                            @Content(schema = @Schema(implementation = TaskResponse.class),
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "parameter is not valid",
                    content = {
                            @Content(schema = @Schema(implementation = String.class),
                                    mediaType = "string")
                    })
    })
    @PatchMapping("/{id}/{newStatus}")
    public TaskResponse changeStatus(@PathVariable Long id, @PathVariable String newStatus, @AuthenticationPrincipal UserDetails userDetails) throws IllegalAccessException {
        log.info("changeStatus() is called");

        if(isNotAuthor(id, userDetails) && isNotPerformer(id, userDetails)) {
            throw new IllegalAccessException("Изменить статус задачи может только ее автор или исполнитель");
        }

        return taskMapper.taskToResponse(
                taskService.changeStatus(id, newStatus));
    }

    @Operation(
            summary = "delete task",
            description = "delete task by id",
            tags = {"id"}
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) throws IllegalAccessException {
        log.info("delete() is called");

        if(isNotAuthor(id, userDetails)) {
            throw new IllegalAccessException("Удалить задачу может только ее автор");
        }

        taskService.deleteById(id);
    }



    private boolean isNotAuthor(Long id, UserDetails userDetails){
        log.info("isAuthor() is called");

        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        Long authorId = taskService.findById(id).getAuthor().getId();

        return !Objects.equals(userId, authorId);
    }

    private boolean isNotPerformer(Long id, UserDetails userDetails){
        log.info("isPerformer() is called");

        Long userId = userService.findByUsername(userDetails.getUsername()).getId();
        Long performerId = taskService.findById(id).getPerformer().getId();

        return !Objects.equals(userId, performerId);
    }
}
