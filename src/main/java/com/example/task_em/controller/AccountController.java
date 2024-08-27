package com.example.task_em.controller;

import com.example.task_em.dto.TaskResponse;
import com.example.task_em.dto.UserRequest;
import com.example.task_em.dto.UserResponse;
import com.example.task_em.mapper.UserMapper;
import com.example.task_em.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
@Tags({
        @Tag(name = "Account controller", description = "Account controller version 1.0"),
        @Tag(name = "user")
})
public class AccountController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201", description = "create new user",
                    content = {
                            @Content(schema = @Schema(implementation = TaskResponse.class),
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "400", description = "parameter is not valid",
                    content = {
                            @Content(schema = @Schema(implementation = String.class),
                                    mediaType = "string")
                    })
    })
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
