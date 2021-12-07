package com.example.demo.mapper;

import org.mapstruct.Mapper;

import com.example.demo.dto.DoctorSimpleDTO;
import com.example.demo.entity.DoctorEntity;

@Mapper(componentModel = "spring")
public interface DoctorEntitySimpleDtoMapper {
	DoctorSimpleDTO toDto(DoctorEntity entity);

	DoctorEntity toEntity(DoctorSimpleDTO dto);
}
