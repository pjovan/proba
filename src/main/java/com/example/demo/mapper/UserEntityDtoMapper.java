package com.example.demo.mapper;

import org.mapstruct.Mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserEntityDtoMapper {
	UserDTO toDTO(UserEntity entity);

	UserEntity toEntity(UserDTO user);
}
