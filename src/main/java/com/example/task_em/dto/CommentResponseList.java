package com.example.task_em.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentResponseList {

    List<CommentResponse> comments;
}
