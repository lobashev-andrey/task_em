package com.example.task_em.controller;

import com.example.task_em.dto.CommentRequest;
import com.example.task_em.dto.CommentResponse;
import com.example.task_em.dto.CommentResponseList;
import com.example.task_em.entity.Comment;
import com.example.task_em.mapper.CommentMapper;
import com.example.task_em.service.CommentService;
import com.example.task_em.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @GetMapping("/{taskId}")
    public CommentResponseList findAllByTaskId (@PathVariable Long taskId) {
        log.info("findAllByTaskId() is called");

        return commentMapper.commentListToResponseList(
                commentService.findByTaskId(taskId));
    }

    @PostMapping
    public CommentResponse create (@Valid @RequestBody CommentRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("create() is called");

        Comment comment = commentMapper.requestToComment(request);
        comment.setUser(userService.findByUsername(userDetails.getUsername()));

        return commentMapper.commentToResponse(
                commentService.create(comment));
    }

    // Остальные методы в ТЗ не указаны
}
