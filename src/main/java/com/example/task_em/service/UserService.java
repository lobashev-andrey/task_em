package com.example.task_em.service;

import com.example.task_em.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    User create(User user);
}
