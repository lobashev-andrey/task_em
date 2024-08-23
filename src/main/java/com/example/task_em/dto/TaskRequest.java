package com.example.task_em.dto;

import com.example.task_em.entity.Priority;
import com.example.task_em.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    private String title;

    private String description;

    private Status status;

    private Priority priority;

    private Long author;

    private Long performer;
}
