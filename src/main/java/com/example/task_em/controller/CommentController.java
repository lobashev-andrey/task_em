package com.example.task_em.controller;

import com.example.task_em.dto.CommentRequest;
import com.example.task_em.dto.CommentResponse;
import com.example.task_em.dto.CommentResponseList;
import com.example.task_em.entity.Comment;
import com.example.task_em.mapper.CommentMapper;
import com.example.task_em.service.CommentService;
import com.example.task_em.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tags({
        @Tag(name = "Comment controller", description = "Comment controller version 1.0"),
        @Tag(name = "comment")
})
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @Operation(
            summary = "get comments",
            description = "get all comments by taskId"
    )
    @GetMapping("/{taskId}")
    public CommentResponseList findAllByTaskId (@PathVariable Long taskId) {
        log.info("findAllByTaskId() is called");

        return commentMapper.commentListToResponseList(
                commentService.findByTaskId(taskId));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "create comment",
                    content = {
                            @Content(schema = @Schema(implementation = CommentResponse.class),
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "some parameter is not found in DB",
                    content = {
                            @Content(schema = @Schema(implementation = String.class),
                                    mediaType = "string")
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "some parameter is not valid",
                    content = {
                            @Content(schema = @Schema(implementation = String.class),
                                    mediaType = "string")
                    })
    })
    @PostMapping
    public CommentResponse create (@Valid @RequestBody CommentRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("create() is called");

        Comment comment = commentMapper.requestToComment(request);
        comment.setUser(userService.findByUsername(userDetails.getUsername()));

        return commentMapper.commentToResponse(
                commentService.create(comment));
    }

    // Остальные методы в ТЗ не указаны
}
