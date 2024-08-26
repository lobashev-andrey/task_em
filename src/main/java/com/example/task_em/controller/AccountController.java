package com.example.task_em.controller;

import com.example.task_em.dto.UserRequest;
import com.example.task_em.dto.UserResponse;
import com.example.task_em.mapper.UserMapper;
import com.example.task_em.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        log.info("create() method is called");

        usernameAndEmailCheck(request);

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.userToResponse(
                        userService.create(
                                userMapper.requestToUser(request)));
    }

    private void usernameAndEmailCheck(UserRequest request) {
        if(userService.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Этот логин уже используется. Придумайте другой.");
        }
        if(userService.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Этот email уже зарегистрирован. Введите другой.");
        }
    }

}
