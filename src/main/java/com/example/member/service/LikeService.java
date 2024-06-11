package com.example.member.service;

import com.example.member.entity.LikeEntity;
import com.example.member.entity.TodoEntity;
import com.example.member.mapper.LikeMapper;
import com.example.member.repository.LikeRepository;
import com.example.member.dto.LikeDTO;
import com.example.member.repository.TodoRepository;
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
    private final TodoRepository todoRepository;

    public ResponseData<LikeEntity> likeCheck(LikeDTO likeDTO) {
        Optional<LikeEntity> likeInfo = likeRepository.findByLikeMemberIdAndLikeTodoId(likeDTO.getLikeMemberId(), likeDTO.getLikeTodoId());
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(likeDTO.getLikeTodoId());
        if (likeInfo.isEmpty()) {
            LikeEntity likeEntity = LikeMapper.INSTANCE.toEntity(likeDTO);
            likeEntity = likeRepository.save(likeEntity);
            return ResponseData.res(StatusCode.OK, Success.TRUE);
        } else {
            likeRepository.delete(likeInfo.get());
            return ResponseData.res(StatusCode.OK, Success.TRUE);
        }
    }

}
