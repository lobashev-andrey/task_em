package com.example.task_em.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Task> createdTasks = new HashSet<>();

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL)
    private Set<Task> assignedTasks = new HashSet<>();
}
