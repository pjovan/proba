package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PatientDTO;
import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.NurseEntity;
import com.example.demo.entity.PatientEntity;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PatientEntityDtoMapper;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.NurseRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.util.JwtUtil;

@Service
@Transactional
public class PatientService {

	PatientRepository patientRepository;
	DoctorRepository doctorRepository;
	NurseRepository nurseRepository;
	PatientEntityDtoMapper patientEntityDtoMapper;
	JwtUtil jwt;

	@Autowired
	public PatientService(PatientRepository patientRepository, PatientEntityDtoMapper patientEntityDtoMapper,
			JwtUtil jwt, DoctorRepository doctorRepository, NurseRepository nurseRepository) {
		super();
		this.patientRepository = patientRepository;
		this.patientEntityDtoMapper = patientEntityDtoMapper;
		this.jwt = jwt;
		this.doctorRepository = doctorRepository;
		this.nurseRepository = nurseRepository;
	}

	public PatientDTO findById(Long id, String token) {

		String username = jwt.extractUsername(token);

		Optional<DoctorEntity> entity = doctorRepository.findByUsername(username);
		Optional<NurseEntity> entityNurse = nurseRepository.findByUsername(username);

		if (entity.isEmpty() && entityNurse.isEmpty()) {
			throw new ResourceNotFoundException("You don't have permission for this action.");
		}

		Optional<PatientEntity> patientEntity = patientRepository.findById(id);
		if (patientEntity.isEmpty()) {
			throw new ResourceNotFoundException("Patient doesn't exist.");
		}
		return patientEntityDtoMapper.toDto(patientEntity.get());
	}

	public List<PatientDTO> getAll(String token) {
		String username = jwt.extractUsername(token);

		Optional<DoctorEntity> entityDoctor = doctorRepository.findByUsername(username);
		Optional<NurseEntity> entityNurse = nurseRepository.findByUsername(username);

		if (entityDoctor.isEmpty() && entityNurse.isEmpty()) {
			throw new ResourceNotFoundException("You don't have permission for this action.");
		}

		List<PatientEntity> entities = patientRepository.findAll();
		return entities.stream().map(entity -> {
			return patientEntityDtoMapper.toDto(entity);
		}).collect(Collectors.toList());
	}

	public PatientDTO save(PatientDTO dto, String token) {
		String username = jwt.extractUsername(token);

		Optional<NurseEntity> entityNurse = nurseRepository.findByUsername(username);

		if (entityNurse.isEmpty()) {
			throw new ResourceNotFoundException("You don't have permission for this action.");
		}

		Optional<PatientEntity> entity = patientRepository.findByJmbg(dto.getJmbg());
		if (entity.isPresent()) {
			throw new ResourceAlreadyExistsException(dto.getJmbg(), "Patient with this jmbg already exists");
		}
		PatientEntity patient = patientRepository.save(patientEntityDtoMapper.toEntity(dto));
		return patientEntityDtoMapper.toDto(patient);
	}

	public PatientDTO update(PatientDTO dto, String token) {
		String username = jwt.extractUsername(token);

		Optional<NurseEntity> entityNurse = nurseRepository.findByUsername(username);

		if (entityNurse.isEmpty()) {
			throw new ResourceNotFoundException("You don't have permission for this action.");
		}

		Optional<PatientEntity> entity = patientRepository.findById(dto.getId());
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("Patient doesn't exist!");
		}
		entity.get().setName(dto.getName());

		PatientEntity patient = patientRepository.save(entity.get());
		return patientEntityDtoMapper.toDto(patient);

	}

	public PatientDTO delete(Long id, String token) {
		String username = jwt.extractUsername(token);

		Optional<NurseEntity> entityNurse = nurseRepository.findByUsername(username);

		if (entityNurse.isEmpty()) {
			throw new ResourceNotFoundException("You don't have permission for this action.");
		}

		Optional<PatientEntity> patientEntity = patientRepository.findById(id);
		if (patientEntity.isEmpty()) {
			throw new ResourceNotFoundException("Patient with id " + id + " does not exist!");
		}

		patientRepository.delete(patientEntity.get());

		return patientEntityDtoMapper.toDto(patientEntity.get());

	}

}
