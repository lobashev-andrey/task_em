package com.example.task_em.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @NotNull(message = "Укажите id задачи.")
    private Long taskId;

    @NotEmpty(message = "Поле текста комментария не должно быть пустым.")
    private String text;
}
