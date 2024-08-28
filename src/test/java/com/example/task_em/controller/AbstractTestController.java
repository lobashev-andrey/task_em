package com.example.task_em.controller;


import com.example.task_em.dto.*;
import com.example.task_em.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AbstractTestController {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;



    protected User createUser(Long id){
        return new User(
                id,
                "user" + id,
                "mail" + id + "@example.com",
                "password" + id,
                new HashSet<>(), new HashSet<>(), new HashSet<>()
        );
    }

    protected Task createTask(Long id, User author, User performer){
        return new Task(id,"title" + id, "description" + id, Status.в_ожидании, Priority.средний, author, performer, new ArrayList<>()
        );
    }

    protected UserResponse createClientResponse(Long id){
        return new UserResponse(
                id,
                "user" + id,
                "mail" + 1 + "@example.com"
        );
    }

    protected TaskResponse createTaskResponse(Long id, User author, User performer, CommentResponseList commentResponseList){
        return new TaskResponse(
                id,
                "title" + id,
                "description" + id,
                "в_ожидании",
                "средний",
                author.getId(),
                performer.getId(),
                commentResponseList
        );
    }

    protected Comment createComment(Long id, User author, Task task, String text) {
        return new Comment(id, author, task, text);
    }

    protected CommentResponse createCommentResponse(Long id, User author, Task task, String text) {
        return new CommentResponse(id, author.getId(), task.getId(), text);
    }

}

