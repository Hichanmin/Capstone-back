package com.example.member.mapper;

import com.example.member.dto.UpdateDTO;
import org.mapstruct.Mapper;
import com.example.member.entity.TodoEntity;
import org.mapstruct.factory.Mappers;

@Mapper

public interface UpdateMapper {
    UpdateMapper INSTANCE = Mappers.getMapper(UpdateMapper.class);

    TodoEntity toEntity(UpdateDTO updateDTO);

}
