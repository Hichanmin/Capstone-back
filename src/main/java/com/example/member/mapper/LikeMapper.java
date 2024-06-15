package com.example.member.mapper;

import com.example.member.dto.LikeDTO;
import com.example.member.entity.LikeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LikeMapper {
    LikeMapper INSTANCE = Mappers.getMapper(LikeMapper.class);

    LikeEntity toEntity(LikeDTO likeDTO);
    LikeDTO toDTO(LikeEntity likeEntity);
}
