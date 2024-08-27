package com.example.task_em.filter;

import com.example.task_em.entity.Priority;
import com.example.task_em.entity.Status;
import com.example.task_em.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskFilter {

    private Integer pageSize = 10;

    private Integer pageNumber = 0;

    private String title;

    private String description;

    private String status;

    private String priority;

    private Long authorId;

    private Long performerId;

    private String author;

    private String performer;
}
