package com.example.member.repository;

import com.example.member.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByCommentTodoId(Long commentTodoId);
    Optional<CommentEntity> findByCommentId(Long commentId);
}
