package com.example.task_em.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TaskResponseList {

    private List<TaskResponse> tasks;
}
