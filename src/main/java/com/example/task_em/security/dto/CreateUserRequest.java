package com.example.task_em.security.dto;

import com.example.task_em.entity.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    @NotEmpty(message = "Укажите имя пользователя.")
    @Pattern(regexp = "\\w{5,15}", message = "Имя пользователя должно быть длиной 5-15 символов. \nДопускаются латинские буквы, цифры и нижнее подчеркивание.")
    private String username;

    @NotNull(message = "Укажите адрес электронной почты.")
    @Email(message = "Неправильный формат адреса электронной почты.")
    private String email;

    @NotEmpty(message = "Список ролей пользователя не должен быть пустым")
    private Set<RoleType> roles;

    @NotEmpty(message = "Укажите пароль.")
    @Pattern(regexp = "\\S{5,15}", message = "Пароль должен быть длиной 5-15 символов. \nДопускаются любые символы кроме пробелов и обратного слэша.")
    private String password;
}

