package com.example.member.mapper;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// Entity DTO 변환
@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberEntity toEntity(MemberDTO memberDTO);
    MemberDTO toDTO(MemberEntity memberEntity);
}