package com.example.member.mapper;

import com.example.member.dto.AllTodoDTO;
import com.example.member.dto.ResponseMyTodoDTO;
import com.example.member.dto.TodoCreateDTO;
import com.example.member.dto.TodoDTO;
import com.example.member.entity.TodoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// Entity DTO 변환
@Mapper
public interface TodoMapper {
    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    TodoEntity toEntity(TodoDTO todoDTO);
    TodoDTO toDTO(TodoEntity todoEntity);
    ResponseMyTodoDTO toMyListDTO(TodoEntity todoEntity);
    AllTodoDTO toAllTodoDTO(TodoEntity todoEntity);
    TodoEntity totoEntity(TodoCreateDTO todoCreateDTO);
}