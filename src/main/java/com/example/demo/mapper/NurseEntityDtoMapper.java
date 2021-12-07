package com.example.demo.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.example.demo.dto.NurseDTO;
import com.example.demo.entity.NurseEntity;

@Mapper(componentModel = "spring", uses = {
		UserEntityDtoMapper.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface NurseEntityDtoMapper {

	NurseDTO toDto(NurseEntity nurse);

	NurseEntity toEntity(NurseDTO nurse);
}
