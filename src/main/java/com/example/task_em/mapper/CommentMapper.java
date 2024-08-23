package com.example.task_em.mapper;

import com.example.task_em.dto.CommentRequest;
import com.example.task_em.dto.CommentResponse;
import com.example.task_em.dto.CommentResponseList;
import com.example.task_em.entity.Comment;
import com.example.task_em.service.TaskService;
import com.example.task_em.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserService userService;
    private final TaskService taskService;

    public Comment requestToComment(CommentRequest request) {
        return Comment.builder()
                .user(userService.findById(request.getUserId()))
                .task(taskService.findById(request.getTaskId()))
                .text(request.getText())
                .build();
    }

    public CommentResponse commentToResponse (Comment comment) {

        return new CommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getTask().getId(),
                comment.getText()
        );
    }

    public CommentResponseList commentListToResponseList (List<Comment> comments) {
        List<CommentResponse> responses = comments.stream().map(this::commentToResponse).toList();

        return new CommentResponseList(responses);
    }
}
