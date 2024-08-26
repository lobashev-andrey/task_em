package com.example.task_em.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotEmpty(message = "Укажите имя пользователя.")
    @Pattern(regexp = "\\w{5,15}", message = "Имя пользователя должно быть длиной 5-15 символов. \nДопускаются латинские буквы, цифры и нижнее подчеркивание.")
    private String username;

    @NotNull(message = "Укажите адрес электронной почты.")
    @Email(message = "Неправильный формат адреса электронной почты.")
    private String email;

    @NotEmpty(message = "Укажите пароль.")
    @Pattern(regexp = "\\S{5,15}", message = "Пароль должен быть длиной 5-15 символов. \nДопускаются любые символы кроме пробелов и обратного слэша.")
    private String password;
}
