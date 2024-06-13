package com.example.member.repository;

import com.example.member.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByLikeMemberIdAndLikeTodoId(Long likeMemberId, Long likeTodoId);
}
