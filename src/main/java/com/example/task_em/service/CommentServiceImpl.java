package com.example.task_em.service;

import com.example.task_em.entity.Comment;
import com.example.task_em.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    @Override
    public List<Comment> findByTaskId(Long taskId) {
        log.info(MessageFormat.format("findByTaskId() is called with taskId = {0}", taskId));

        return commentRepository.findAllByTaskId(taskId);
    }

    @Override
    public Comment create(Comment comment) {
        log.info("create() is called with ID = {0}");

        return commentRepository.save(comment);
    }
}
