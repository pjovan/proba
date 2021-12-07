package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.NurseDTO;
import com.example.demo.entity.NurseEntity;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.HospitalEntityDtoMapper;
import com.example.demo.mapper.NurseEntityDtoMapper;
import com.example.demo.repository.NurseRepository;
import com.example.demo.util.JwtUtil;

@Service
@Transactional
public class NurseService {

	NurseRepository nurseRepository;
	NurseEntityDtoMapper nurseEntityDtoMapper;
	HospitalEntityDtoMapper hospitalMapper;
	JwtUtil jwt;

	@Autowired
	public NurseService(NurseRepository nurseRepository, NurseEntityDtoMapper nurseEntityDtoMapper,
			HospitalEntityDtoMapper hospitalMapper, JwtUtil jwtUtil) {
		super();
		this.nurseRepository = nurseRepository;
		this.nurseEntityDtoMapper = nurseEntityDtoMapper;
		this.hospitalMapper = hospitalMapper;
		this.jwt = jwtUtil;
	}

	public NurseDTO findById(Long id) {
		Optional<NurseEntity> nurse = nurseRepository.findById(id);
		if (nurse.isEmpty()) {
			throw new ResourceNotFoundException("Nurse doesn't exist.");
		}

		return nurseEntityDtoMapper.toDto(nurse.get());
	}

	public NurseDTO findByUsername(String token) {
		String username = jwt.extractUsername(token);
		Optional<NurseEntity> entity = nurseRepository.findByUsername(username);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("Nurse doesn't exist");
		}

		NurseDTO dto = nurseEntityDtoMapper.toDto(entity.get());
		dto.setPassword("");
		return dto;
	}

	public List<NurseDTO> getAll() {
		List<NurseEntity> entities = nurseRepository.findAll();
		return entities.stream().map(nurseEntityDtoMapper::toDto).collect(Collectors.toList());
	}

	public NurseDTO save(NurseDTO dto) {
		Optional<NurseEntity> entity = nurseRepository.findById(dto.getId());
		if (entity.isPresent()) {
			throw new ResourceAlreadyExistsException(dto.getUsername(), "Nurse already exists");
		}
		NurseEntity nurse = nurseRepository.save(nurseEntityDtoMapper.toEntity(dto));
		return nurseEntityDtoMapper.toDto(nurse);
	}

	public NurseDTO update(NurseDTO dto, String token) {
		Optional<NurseEntity> existingNurse = nurseRepository.findById(dto.getId());
		if (existingNurse.isEmpty()) {
			throw new ResourceNotFoundException("Nurse doesn't exist.");
		}

		String username = jwt.extractUsername(token);
		Optional<NurseEntity> entity = nurseRepository.findByUsername(username);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("Nurse doesn't exist");
		}

		NurseEntity nurse = existingNurse.get();
		nurse.setName(dto.getName());
		nurse.setUsername(dto.getUsername());
//		nurse.setPassword(dto.getPassword());
//		nurse.setHospital(hospitalMapper.toEntity(dto.getHospital()));

		return nurseEntityDtoMapper.toDto(nurseRepository.save(nurse));
	}

	public NurseDTO delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
