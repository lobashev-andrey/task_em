package com.example.task_em.controller;

import com.example.task_em.dto.TaskResponse;
import com.example.task_em.dto.UserResponse;
import com.example.task_em.dto.UserResponseList;
import com.example.task_em.mapper.UserMapper;
import com.example.task_em.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Tags({
        @Tag(name = "User controller", description = "User controller version 1.0"),
        @Tag(name = "user")
})
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @Operation(
            summary = "get users",
            description = "get all users"
    )
    @GetMapping
    public UserResponseList findAll() {
        log.info("findAll() is called");

        return userMapper.userListToResponseList(
                userService.findAll());
    }

    @Tag(name = "id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "get user by id",
                    content = {
                            @Content(schema = @Schema(implementation = TaskResponse.class),
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = {
                            @Content(schema = @Schema(implementation = String.class),
                                    mediaType = "application/json")
                    })
    })
    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        log.info("findById() is called");

        return userMapper.userToResponse(
                userService.findById(id));
    }
}
