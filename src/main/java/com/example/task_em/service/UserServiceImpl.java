package com.example.task_em.service;

import com.example.task_em.entity.RoleType;
import com.example.task_em.entity.User;
import com.example.task_em.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;


    @Override
    public List<User> findAll() {
        log.info("findAll() is called with ID = {0}");

        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        log.info(MessageFormat.format("findById() is called with ID = {0}", id));

        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Пользователь с ID {0} не найден", id)));
    }

    @Override
    public User findByUsername(String username) {
        log.info(MessageFormat.format("findByUsername()) is called with username = {0}", username));

        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Пользователь с юзернеймом {0} не найден", username)));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public User create(User user) {
        log.info("create() is called");

        Set<RoleType> roles = user.getRoles();
        roles.add(RoleType.ROLE_USER);
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
