package com.example.demo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SpecializationDTO;
import com.example.demo.entity.SpecializationEntity;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.mapper.SpecializationEntityDtoMapper;
import com.example.demo.repository.SpecializationRepository;

@Service
@Transactional
public class SpecializationService {
	SpecializationRepository specializationRepository;
	SpecializationEntityDtoMapper specializationDtoMapper;

	@Autowired
	public SpecializationService(SpecializationRepository specializationRepository,
			SpecializationEntityDtoMapper specializationDtoMapper) {
		super();
		this.specializationRepository = specializationRepository;
		this.specializationDtoMapper = specializationDtoMapper;
	}

	public SpecializationDTO save(SpecializationDTO dto) {
		Optional<SpecializationEntity> exisitingSpecialization = specializationRepository.findByName(dto.getName());
		if (exisitingSpecialization.isPresent()) {
			throw new ResourceAlreadyExistsException(dto.getName(), "This specialization already exists.");
		}
		SpecializationEntity specialization = specializationRepository.save(specializationDtoMapper.toEntity(dto));
		return specializationDtoMapper.toDto(specialization);
	}

}
