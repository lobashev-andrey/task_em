package com.example.task_em.controller;

import com.example.task_em.dto.UserRequest;
import com.example.task_em.dto.UserResponse;
import com.example.task_em.dto.UserResponseList;
import com.example.task_em.mapper.UserMapper;
import com.example.task_em.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping
    public UserResponseList findAll() {
        return userMapper.userListToResponseList(
                userService.findAll());
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userMapper.userToResponse(
                userService.findById(id));
    }

    @PostMapping
    public UserResponse create(@RequestBody UserRequest request) {
        return userMapper.userToResponse(
                userService.create(
                        userMapper.requestToUser(request)));
    }

}
