package com.example.task_em.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private String status;

    private String priority;

    private Long authorId;

    private Long performerId;

    private CommentResponseList comments;
}
