package com.example.task_em.service;

import com.example.task_em.entity.Comment;
import com.example.task_em.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    @Override
    public List<Comment> findByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId);
    }

    @Override
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }
}
