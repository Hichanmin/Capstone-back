package com.example.member.repository;

import com.example.member.entity.LikeEntity;
import com.example.member.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByLikeEmailAndLikeTodoId(String likeEmail, String likeTodoId);
}
