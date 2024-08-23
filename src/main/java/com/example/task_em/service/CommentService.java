package com.example.task_em.service;

import com.example.task_em.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findByTaskId(Long taskId);

    Comment create(Comment comment);
}
