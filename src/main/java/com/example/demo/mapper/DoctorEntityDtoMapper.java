package com.example.demo.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.example.demo.dto.DoctorDTO;
import com.example.demo.entity.DoctorEntity;

@Mapper(componentModel = "spring", uses = { DoctorEntitySimpleDtoMapper.class, PatientEntitySimpleDtoMapper.class,
		UserEntityDtoMapper.class,
		ExaminationEntityDtoMapper.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DoctorEntityDtoMapper {
	DoctorDTO toDto(DoctorEntity entity);

	DoctorEntity toEntity(DoctorDTO dto);
}
