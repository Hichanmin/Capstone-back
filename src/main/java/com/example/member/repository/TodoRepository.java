package com.example.member.repository;

import com.example.member.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    Optional<List<TodoEntity>> findByTodoEmail(String todoEmail);
    Optional<TodoEntity> findByIdAndTodoEmail(Long id, String todoEmail);
    Optional<List<TodoEntity>> findByTodoTitleContaining(String todoTitle);
    Optional<List<TodoEntity>> findByTodoCategoryContaining(String todoCategoy);
    Optional<List<TodoEntity>> findByTodoDateAndTodoEmail(String todoDate, String todoEmail);
}