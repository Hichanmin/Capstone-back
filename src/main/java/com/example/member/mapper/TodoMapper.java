package com.example.member.mapper;

import com.example.member.dto.MemberDTO;
import com.example.member.dto.TodoDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.entity.TodoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// Entity DTO 변환
@Mapper
public interface TodoMapper {
    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    TodoEntity toEntity(TodoDTO todoDTO);
    TodoDTO toDTO(TodoEntity todoEntity);
}