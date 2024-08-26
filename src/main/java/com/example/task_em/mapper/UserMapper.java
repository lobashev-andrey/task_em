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
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public UserResponse userToResponse (User user) {

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().toList().get(0).name()
        );
    }

    public UserResponseList userListToResponseList(List<User> users) {
        List<UserResponse> responses = users.stream().map(this::userToResponse).toList();
        return new UserResponseList(responses);
    }
}
