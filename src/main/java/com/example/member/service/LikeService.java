package com.example.member.service;

import com.example.member.entity.LikeEntity;
import com.example.member.repository.LikeRepository;
import com.example.member.dto.LikeDTO;
import com.example.member.response.ResponseData;
import com.example.member.response.StatusCode;
import com.example.member.response.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    public boolean likeCheck(LikeDTO likeDTO) {
        Optional<LikeEntity> byLikeEmailAndLikeTodoId = likeRepository.findByLikeEmailAndLikeTodoId(likeDTO.getLikeEmail(), likeDTO.getLikeTodoId());
        return byLikeEmailAndLikeTodoId.isPresent();
    }
}
