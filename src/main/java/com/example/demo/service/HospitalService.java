package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DoctorDTO;
import com.example.demo.dto.HospitalDTO;
import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.HospitalEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.DoctorEntityDtoMapper;
import com.example.demo.mapper.HospitalEntityDtoMapper;
import com.example.demo.mapper.NurseEntityDtoMapper;
import com.example.demo.repository.HospitalRepozitory;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;

@Service
@Transactional
public class HospitalService {
	HospitalRepozitory hospitalRepozitory;
	HospitalEntityDtoMapper hospitalEntityDtoMapper;
	DoctorEntityDtoMapper doctorEntityDtoMapper;
	NurseEntityDtoMapper nurseEntityDtoMapper;
	UserRepository userRepository;
	JwtUtil jwt;

	@Autowired
	public HospitalService(HospitalRepozitory hospitalRepozitory, HospitalEntityDtoMapper hospitalEntityDtoMapper,
			DoctorEntityDtoMapper doctorEntityDtoMapper, NurseEntityDtoMapper nurseEntityDtoMapper, JwtUtil jwt,
			UserRepository userRepository) {
		super();
		this.hospitalRepozitory = hospitalRepozitory;
		this.hospitalEntityDtoMapper = hospitalEntityDtoMapper;
		this.doctorEntityDtoMapper = doctorEntityDtoMapper;
		this.nurseEntityDtoMapper = nurseEntityDtoMapper;
		this.jwt = jwt;
		this.userRepository = userRepository;
	}

	public HospitalDTO findById(Long id) {
		Optional<HospitalEntity> hospital = hospitalRepozitory.findById(id);
		if (hospital.isEmpty()) {
			throw new ResourceNotFoundException("This hospital doesn't exist.");
		}

		return hospitalEntityDtoMapper.toDto(hospital.get());
	}

	public List<HospitalDTO> findByDoctor(DoctorDTO dto, String token) {
		String adminUsername = jwt.extractUsername(token);
		Optional<UserEntity> adminEntity = userRepository.findByUsername(adminUsername);
		if (adminEntity.isEmpty()) {
			throw new ResourceNotFoundException("User: " + adminUsername + " is not in our database!");
		}

		DoctorEntity doctor = doctorEntityDtoMapper.toEntity(dto);
		List doctors = new ArrayList<DoctorEntity>();
		doctors.add(doctor);
		List<HospitalEntity> hospitals = hospitalRepozitory.findAllByDoctorsIn(doctors);
		if (hospitals.isEmpty()) {
			return new ArrayList<>();
		}

		return hospitals.stream().map(hospitalEntityDtoMapper::toDto).collect(Collectors.toList());
	}

	public List<HospitalDTO> getAll(String token) {
		String adminUsername = jwt.extractUsername(token);
		Optional<UserEntity> adminEntity = userRepository.findByUsername(adminUsername);
		if (adminEntity.isEmpty()) {
			throw new ResourceNotFoundException("User: " + adminUsername + " is not in our database!");
		}
		List<HospitalEntity> entities = hospitalRepozitory.findAll();
		return entities.stream().map(hospitalEntityDtoMapper::toDto).collect(Collectors.toList());
	}

	public void save(HospitalDTO dto) {
		Optional<HospitalEntity> hospital = hospitalRepozitory.findByName(dto.getName());
		if (hospital.isPresent()) {
			throw new ResourceAlreadyExistsException(dto.getName(), "Hospital with this name already exists.");
		}
		hospitalRepozitory.save(hospitalEntityDtoMapper.toEntity(dto));
	}

	public Optional<HospitalDTO> update(HospitalDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	public HospitalDTO delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
