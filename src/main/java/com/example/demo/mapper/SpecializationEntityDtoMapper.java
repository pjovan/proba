package com.example.demo.mapper;

import org.mapstruct.Mapper;

import com.example.demo.dto.SpecializationDTO;
import com.example.demo.entity.SpecializationEntity;

@Mapper(componentModel = "spring")
public interface SpecializationEntityDtoMapper {

	SpecializationDTO toDto(SpecializationEntity specialization);

	SpecializationEntity toEntity(SpecializationDTO specialization);
}
