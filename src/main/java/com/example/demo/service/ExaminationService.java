package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ExaminationDTO;
import com.example.demo.dto.UpdateDTO;
import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.ExaminationEntity;
import com.example.demo.entity.ExaminationId;
import com.example.demo.entity.HospitalEntity;
import com.example.demo.entity.NurseEntity;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ExaminationEntityDtoMapper;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.ExaminationRepository;
import com.example.demo.repository.HospitalRepozitory;
import com.example.demo.repository.NurseRepository;
import com.example.demo.util.JwtUtil;

@Service
@Transactional
public class ExaminationService {

	ExaminationRepository examinationRepository;
	ExaminationEntityDtoMapper mapper;
	DoctorRepository doctorRepository;
	NurseRepository nurseRepository;
	HospitalRepozitory hospitalRepozitory;
	JwtUtil jwt;

	@Autowired
	public ExaminationService(ExaminationRepository examinationRepository, ExaminationEntityDtoMapper mapper,
			DoctorRepository doctorRepository, NurseRepository nurseRepository, JwtUtil jwt,
			HospitalRepozitory hospitalRepozitory) {
		this.doctorRepository = doctorRepository;
		this.nurseRepository = nurseRepository;
		this.jwt = jwt;
		this.examinationRepository = examinationRepository;
		this.hospitalRepozitory = hospitalRepozitory;
		this.mapper = mapper;
	}

	public Optional<ExaminationDTO> findById(ExaminationId id, String token) {
		String username = jwt.extractUsername(token);

		Optional<DoctorEntity> entityDoctor = doctorRepository.findByUsername(username);
		Optional<NurseEntity> entityNurse = nurseRepository.findByUsername(username);

		if (entityDoctor.isEmpty() && entityNurse.isEmpty()) {
			throw new ForbiddenException("You don't have permission for this action.");
		}

		Optional<ExaminationEntity> entity = examinationRepository.findById(id);
		if (entity.isPresent()) {
			return Optional.of(mapper.toDto(entity.get()));
		}
		return Optional.empty();
	}

	public List<ExaminationDTO> getAll(Long doctorId, String token) {

		String username = jwt.extractUsername(token);

		Optional<DoctorEntity> entityDoctor = doctorRepository.findById(doctorId);
		Optional<NurseEntity> entityNurse = nurseRepository.findByUsername(username);

		if ((entityDoctor.isEmpty() && entityNurse.isEmpty()) || (entityDoctor.get().getUsername() != username)) {
			throw new ForbiddenException("You don't have permission for this action.");
		}
		return examinationRepository.findByDoctor(entityDoctor.get()).stream().map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public ExaminationDTO save(ExaminationDTO dto) {
		examinationRepository.save(mapper.toEntity(dto));
		return dto;
	}

	public ExaminationDTO updateDiagnosis(UpdateDTO dto, String token) {
		String username = jwt.extractUsername(token);

		Optional<DoctorEntity> entityDoctor = doctorRepository.findByUsername(username);
		Optional<NurseEntity> entityNurse = nurseRepository.findByUsername(username);

		if (entityDoctor.isEmpty() && entityNurse.isEmpty()) {
			throw new ForbiddenException("You don't have permission for this action.");
		}
		Optional<ExaminationEntity> existingExam = examinationRepository
				.findById(new ExaminationId(dto.getDoctorId(), dto.getPatientId(), dto.getDateTime()));
		if (existingExam.isEmpty()) {
			throw new ResourceNotFoundException("There is no such examination.");
		}

		ExaminationEntity exam = existingExam.get();
		exam.setDiagnosis(dto.getDiagnosis());
		examinationRepository.save(exam);

		return mapper.toDto(exam);
	}

	public void delete(ExaminationDTO dto) {
		examinationRepository.delete(mapper.toEntity(dto));
	}

	public List<ExaminationDTO> getByDoctorAndHospital(Long doctorId, Long hospitalId, String token) {

		String username = jwt.extractUsername(token);

		Optional<DoctorEntity> entityDoctor = doctorRepository.findByUsername(username);
		Optional<NurseEntity> entityNurse = nurseRepository.findByUsername(username);

		if (entityDoctor.isEmpty() && entityNurse.isEmpty()) {
			throw new ForbiddenException("You don't have permission for this action.");
		}

		DoctorEntity existingDoctor = doctorRepository.getById(doctorId);
		if (existingDoctor == null) {
			throw new ResourceNotFoundException("There is no such doctor");
		}

		HospitalEntity existingHosptal = hospitalRepozitory.getById(hospitalId);
		if (existingHosptal == null) {
			throw new ResourceNotFoundException("There is no such hospital");
		}

		return examinationRepository.findByHospitalAndDoctor(existingHosptal, existingDoctor).stream()
				.map(mapper::toDto).collect(Collectors.toList());
	}

//	public ExaminationDTO updateDate(UpdateDTO dto) throws Exception {
//		Optional<ExaminationEntity> existingExam = examinationRepository
//				.findById(new ExaminationId(dto.getDoctorId(), dto.getPatientId(), dto.getDate()));
//		if (existingExam.isEmpty()) {
//			throw new Exception("There is no such appointment.");
//		}
//
//		Optional<ExaminationEntity> takenExam = examinationRepository
//				.findById(new ExaminationId(dto.getDoctorId(), dto.getPatientId(), dto.getNewDate()));
//		if (takenExam.isPresent()) {
//			throw new Exception("Appointment with the new date already exists.");
//		}
//		ExaminationEntity exam = existingExam.get();
//		exam.setId(new ExaminationId(dto.getDoctorId(), dto.getPatientId(), dto.getNewDate()));
//		examinationRepository.save(exam);
//
//		return mapper.toDto(exam);
//	}

}
