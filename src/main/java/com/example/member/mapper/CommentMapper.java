package com.example.member.mapper;

import com.example.member.dto.CommentDTO;
import com.example.member.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// Entity DTO 변환
@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentEntity toEntity(CommentDTO CommentDTO);
    CommentDTO toDTO(CommentEntity CommentEntity);
}