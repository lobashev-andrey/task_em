package com.example.task_em.mapper;

import com.example.task_em.dto.CommentResponseList;
import com.example.task_em.dto.TaskRequest;
import com.example.task_em.dto.TaskResponse;
import com.example.task_em.dto.TaskResponseList;
import com.example.task_em.entity.*;
import com.example.task_em.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskMapper {

    private final UserService userService;
    private final CommentMapper commentMapper;

    public Task requestToTask(TaskRequest request) {
        log.info("requestToTask(request) is called");

        User performer = request.getPerformer() == null ? null : userService.findById(request.getPerformer());
        Status status = request.getStatus() == null ? Status.в_ожидании : Status.valueOf(request.getStatus());
        Priority priority = request.getPriority() == null ? Priority.средний : Priority.valueOf(request.getPriority());

        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(status)
                .priority(priority)
                .performer(performer)
                .build();
    }

    public Task requestToTask(TaskRequest request, Long id) {
        log.info(MessageFormat.format("requestToTask(request, id) is called. id = {0}", id));


        Task task = requestToTask(request);
        task.setId(id);
        task.setComments(null);

        return task;
    }
    public TaskResponse taskToResponse(Task task) {
        List<Comment> comments = task.getComments();
        CommentResponseList commentResponseList = commentMapper.commentListToResponseList(comments);

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().toString(),
                task.getPriority().toString(),
                task.getAuthor().getId(),
                task.getPerformer() == null? null : task.getPerformer().getId(),
                commentResponseList
        );
    }

    public TaskResponseList taskListToResponseList(List<Task> tasks) {
        List<TaskResponse> responses = tasks.stream().map(this::taskToResponse).toList();
        return new TaskResponseList(responses);
    }
}
