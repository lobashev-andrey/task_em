package com.example.task_em.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotEmpty(message = "Необходимо указать название задачи")
    @Size(min = 4, message = "Название задачи должно быть не короче 4 символов.")
    private String title;

    @NotEmpty(message = "Необходимо указать описание задачи")
    @Size(min = 10, message = "Описание задачи должно быть не короче 10 символов.")
    private String description;

    @Pattern(regexp = "в_ожидании||в_процессе||завершено", message = "Выберите статус из трех вариантов: 'в_ожидании', 'в_процессе', 'завершено'.")
    private String status;

    @Pattern(regexp = "высокий||средний||низкий", message = "Выберите приоритет из трех вариантов: 'высокий', 'средний', 'низкий'.")
    private String priority;

    private Long performer;
}
