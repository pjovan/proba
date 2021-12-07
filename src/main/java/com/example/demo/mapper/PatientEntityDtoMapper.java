package com.example.demo.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.example.demo.dto.PatientDTO;
import com.example.demo.entity.PatientEntity;

@Mapper(componentModel = "spring", uses = { PatientEntitySimpleDtoMapper.class,
		DoctorEntitySimpleDtoMapper.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PatientEntityDtoMapper {

	PatientDTO toDto(PatientEntity patient);

	PatientEntity toEntity(PatientDTO patient);
}
