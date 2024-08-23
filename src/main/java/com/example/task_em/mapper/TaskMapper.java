package com.example.task_em.mapper;

import com.example.task_em.dto.CommentResponseList;
import com.example.task_em.dto.TaskRequest;
import com.example.task_em.dto.TaskResponse;
import com.example.task_em.dto.TaskResponseList;
import com.example.task_em.entity.Comment;
import com.example.task_em.entity.Priority;
import com.example.task_em.entity.Status;
import com.example.task_em.entity.Task;
import com.example.task_em.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserService userService;
    private final CommentMapper commentMapper;

    public Task requestToTask(TaskRequest request) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(Status.в_ожидании)
                .priority(Priority.средний)
                .author(userService.findById(request.getAuthor()))
                .performer(userService.findById(request.getPerformer()))
                .build();
    }

    public Task requestToTask(TaskRequest request, Long id) {
        Task task = requestToTask(request);
        task.setId(id);

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
                task.getPerformer().getId(),
                commentResponseList
        );
    }

    public TaskResponseList taskListToResponseList(List<Task> tasks) {
        List<TaskResponse> responses = tasks.stream().map(this::taskToResponse).toList();
        return new TaskResponseList(responses);
    }
}
