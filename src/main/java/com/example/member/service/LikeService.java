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

    public ResponseData<LikeEntity> like(LikeDTO likeDTO) {
        Optional<LikeEntity> likeInfo = likeRepository.findByLikeMemberIdAndLikeTodoId(likeDTO.getLikeMemberId(), likeDTO.getLikeTodoId());
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(likeDTO.getLikeTodoId());
        if (likeInfo.isEmpty()) {
            LikeEntity likeEntity = LikeMapper.INSTANCE.toEntity(likeDTO);
            likeEntity = likeRepository.save(likeEntity);
            if (optionalTodoEntity.isPresent()){
                TodoEntity todoEntity = optionalTodoEntity.get();
                int like = todoEntity.getTodoLike() + 1;
                todoEntity.setTodoLike(like);
                todoRepository.save(todoEntity);
            }
        } else {
            likeRepository.delete(likeInfo.get());
            if (optionalTodoEntity.isPresent()){
                TodoEntity todoEntity = optionalTodoEntity.get();
                int like = todoEntity.getTodoLike() - 1;
                todoEntity.setTodoLike(like);
                todoRepository.save(todoEntity);
            }
        }
        return ResponseData.res(StatusCode.OK, Success.TRUE);
    }

}
