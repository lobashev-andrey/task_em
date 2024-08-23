package com.example.task_em.controller;

import com.example.task_em.dto.CommentRequest;
import com.example.task_em.dto.CommentResponse;
import com.example.task_em.dto.CommentResponseList;
import com.example.task_em.mapper.CommentMapper;
import com.example.task_em.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/{taskId}")
    public CommentResponseList findAllByTaskId (@PathVariable Long taskId) {
        return commentMapper.commentListToResponseList(
                commentService.findByTaskId(taskId));
    }

    @PostMapping
    public CommentResponse create (@RequestBody CommentRequest request) {
        return commentMapper.commentToResponse(
                commentService.create(
                        commentMapper.requestToComment(request)));
    }
}
