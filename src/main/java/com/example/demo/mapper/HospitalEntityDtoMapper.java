package com.example.demo.mapper;

import org.mapstruct.Mapper;

import com.example.demo.dto.HospitalDTO;
import com.example.demo.entity.HospitalEntity;

@Mapper(componentModel = "spring")
public interface HospitalEntityDtoMapper {

	public HospitalDTO toDto(HospitalEntity entity);

	public HospitalEntity toEntity(HospitalDTO dto);

}
