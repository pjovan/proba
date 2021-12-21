package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SpecializationDTO;
import com.example.demo.entity.SpecializationEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.SpecializationEntityDtoMapper;
import com.example.demo.repository.SpecializationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;

@Service
@Transactional
public class SpecializationService {
	SpecializationRepository specializationRepository;
	SpecializationEntityDtoMapper specializationDtoMapper;
	UserRepository userRepository;
	JwtUtil jwt;

	@Autowired
	public SpecializationService(SpecializationRepository specializationRepository,
			SpecializationEntityDtoMapper specializationDtoMapper, UserRepository userRepository, JwtUtil jwt) {
		super();
		this.specializationRepository = specializationRepository;
		this.specializationDtoMapper = specializationDtoMapper;
		this.userRepository = userRepository;
		this.jwt = jwt;
	}

	public SpecializationDTO save(SpecializationDTO dto) {
		Optional<SpecializationEntity> exisitingSpecialization = specializationRepository.findByName(dto.getName());
		if (exisitingSpecialization.isPresent()) {
			throw new ResourceAlreadyExistsException(dto.getName(), "This specialization already exists.");
		}
		SpecializationEntity specialization = specializationRepository.save(specializationDtoMapper.toEntity(dto));
		return specializationDtoMapper.toDto(specialization);
	}

	public List<SpecializationDTO> getAll(String token) {
		String adminUsername = jwt.extractUsername(token);
		Optional<UserEntity> adminEntity = userRepository.findByUsername(adminUsername);
		if (adminEntity.isEmpty()) {
			throw new ResourceNotFoundException("User: " + adminUsername + " is not in our database!");
		}
		List<SpecializationEntity> entities = specializationRepository.findAll();
		return entities.stream().map(specializationDtoMapper::toDto).collect(Collectors.toList());
	}

}
