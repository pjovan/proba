package com.example.demo.mapper;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.ExaminationDTO;
import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.ExaminationEntity;
import com.example.demo.entity.PatientEntity;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.ExaminationRepository;
import com.example.demo.repository.PatientRepository;

//@Mapper(componentModel = "spring")
@Component
@Transactional
public class ExaminationEntityDtoMapper {

//	public ExaminationDTO toDto(ExaminationEntity entity);
//
//	public ExaminationEntity toEntity(ExaminationDTO dto);

	@Autowired
	DoctorEntitySimpleDtoMapper doctorMapper;
	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	PatientEntitySimpleDtoMapper patientMapper;
	@Autowired
	PatientRepository patientRepository;

	@Autowired
	private ExaminationRepository examinationRepository;

	public ExaminationDTO toDto(ExaminationEntity examination) {
		ExaminationDTO dto = new ExaminationDTO();
		dto.setId(examination.getId());
		dto.setDoctor(doctorMapper.toDto(examination.getDoctor()));
		dto.setPatient(patientMapper.toDto(examination.getPatient()));
		dto.setDiagnosis(examination.getDiagnosis());
		return dto;
	}

	public ExaminationEntity toEntity(ExaminationDTO examination) {
		ExaminationEntity entity = new ExaminationEntity();
		entity.setId(examination.getId());
		entity.setPatient(patientMapper.toEntity(examination.getPatient()));
		entity.setDiagnosis(examination.getDiagnosis());

//	 dodavanje pregleda u doktora
		Optional<DoctorEntity> existingDoctor = doctorRepository.findById(examination.getDoctor().getId());
		if (existingDoctor.isPresent()) {
			entity.setDoctor(existingDoctor.get());
		} else {
			DoctorEntity doctor = new DoctorEntity();
			doctor = doctorMapper.toEntity(examination.getDoctor());
			entity.setDoctor(doctor);
		}

		Optional<PatientEntity> existingPatient = patientRepository.findById(examination.getPatient().getId());
		if (existingPatient.isPresent()) {
			entity.setPatient(existingPatient.get());
		} else {
			PatientEntity patient = new PatientEntity();
			patient = patientMapper.toEntity(examination.getPatient());
			entity.setPatient(patient);
		}

		return entity;
	}

}
