package com.example.task_em.controller;

import com.example.task_em.dto.UserRequest;
import com.example.task_em.dto.UserResponse;
import com.example.task_em.dto.UserResponseList;
import com.example.task_em.mapper.UserMapper;
import com.example.task_em.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping
    public UserResponseList findAll() {
        log.info("findAll() is called");

        return userMapper.userListToResponseList(
                userService.findAll());
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        log.info("findById() is called");

        return userMapper.userToResponse(
                userService.findById(id));
    }


}
