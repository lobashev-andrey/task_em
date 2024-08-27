package com.example.task_em.filter;

import com.example.task_em.entity.Task;
import org.springframework.data.jpa.domain.Specification;

public interface TaskSpecification {
    static Specification<Task> withFilter(TaskFilter filter) {

        return Specification
                .where(byTitle(filter.getTitle()))
                .and(byDescription(filter.getDescription()))
                .and(byStatus(filter.getStatus()))
                .and(byPriority(filter.getPriority()))
                .and(byAuthorId(filter.getAuthorId()))
                .and(byPerformerId(filter.getPerformerId()))
                .and(byAuthor(filter.getAuthor()))
                .and(byPerformer(filter.getPerformer()))
                ;
    }

    static Specification<Task> byTitle(String title) {
        return (root, query, cb) -> {
            if(title == null) {
                return null;
            }
            return cb.like(root.get("title"), "%" + title + "%");
        };
    }

    static Specification<Task> byDescription(String description) {
        return (root, query, cb) -> {
            if(description == null) { return null; }
            return cb.like(root.get("description"), "%" + description + "%");
        };
    }

    static Specification<Task> byStatus(String status) {
        return (root, query, cb) -> {
            if(status == null) { return null; }
            return cb.equal(root.get("status").get("name"), status);
        };
    }

    static Specification<Task> byPriority(String priority) {
        return (root, query, cb) -> {
            if(priority == null) { return null; }
            return cb.equal(root.get("priority").get("name"), priority);
        };
    }

    static Specification<Task> byAuthorId(Long authorId) {
        return (root, query, cb) -> {
            if(authorId == null) { return null; }
            return cb.equal(root.get("author").get("id"), authorId);
        };
    }

    static Specification<Task> byPerformerId(Long performerId) {
        return (root, query, cb) -> {
            if(performerId == null) { return null; }
            return cb.equal(root.get("performer").get("id"), performerId);
        };
    }

    static Specification<Task> byAuthor(String author) {
        return (root, query, cb) -> {
            if(author == null) { return null; }
            return cb.equal(root.get("author").get("username"), author);
        };
    }

    static Specification<Task> byPerformer(String performer) {
        return (root, query, cb) -> {
            if(performer == null) { return null; }
            return cb.equal(root.get("performer").get("username"), performer);
        };
    }


}
