package com.example.task_em.repository;

import com.example.task_em.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

//    List<Task> findByAuthor(User author);
//
//    List<Task> findByPerformer(User performer);

    @Override
    Page<Task> findAll(Specification<Task> spec, Pageable pageable);
}
