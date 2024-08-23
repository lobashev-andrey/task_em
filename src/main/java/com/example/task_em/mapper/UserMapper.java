package com.example.task_em.mapper;

import com.example.task_em.dto.UserRequest;
import com.example.task_em.dto.UserResponse;
import com.example.task_em.dto.UserResponseList;
import com.example.task_em.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User requestToUser(UserRequest request) {
        return User.builder()
                .username(request.getName())
                .email(request.getEmail())
                .build();
    }

    public UserResponse userToResponse (User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public UserResponseList userListToResponseList(List<User> users) {
        List<UserResponse> responses = users.stream().map(this::userToResponse).toList();
        return new UserResponseList(responses);
    }
}
