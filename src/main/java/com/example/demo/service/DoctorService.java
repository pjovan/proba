package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DoctorDTO;
import com.example.demo.dto.DoctorSimpleDTO;
import com.example.demo.dto.SpecializationDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.ExaminationEntity;
import com.example.demo.entity.NurseEntity;
import com.example.demo.entity.PatientEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.InvalidDateExcpetion;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.DoctorEntityDtoMapper;
import com.example.demo.mapper.ExaminationEntityDtoMapper;
import com.example.demo.mapper.HospitalEntityDtoMapper;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.HospitalRepozitory;
import com.example.demo.repository.NurseRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;

@Service
@Transactional
public class DoctorService {
	DoctorRepository doctorRepository;
	NurseRepository nurseRepository;
	UserRepository userRepository;
	DoctorEntityDtoMapper doctorMapper;
	ExaminationEntityDtoMapper examinationMapper;
	PatientRepository patientRepository;

	HospitalRepozitory hospitalRepozitory;
	HospitalEntityDtoMapper hospitalMapper;
	JwtUtil jwt;

	@Autowired
	public DoctorService(DoctorRepository doctorRepository, DoctorEntityDtoMapper doctorMapper,
			HospitalRepozitory hospitalRepozitory, HospitalEntityDtoMapper hospitalMapper,
			ExaminationEntityDtoMapper examinationMapper, PatientRepository patientRepository, JwtUtil jwt,
			NurseRepository nurseRepository, UserRepository userRepository) {
		super();
		this.doctorRepository = doctorRepository;
		this.hospitalRepozitory = hospitalRepozitory;
		this.doctorMapper = doctorMapper;
		this.hospitalMapper = hospitalMapper;
		this.examinationMapper = examinationMapper;
		this.patientRepository = patientRepository;
		this.jwt = jwt;
		this.nurseRepository = nurseRepository;
		this.userRepository = userRepository;
	}

	public DoctorDTO findByUsername(String token, String username) {

		String adminUsername = jwt.extractUsername(token);
		Optional<UserEntity> adminEntity = userRepository.findByUsername(adminUsername);
		Optional<NurseEntity> nurseEntity = nurseRepository.findByUsername(adminUsername);

		if (adminEntity.isPresent() && adminEntity.get().getUsername().equals(username) == false
				&& nurseEntity.isEmpty()) {
			throw new ForbiddenException("You don't have permission for this action");
		}

		Optional<DoctorEntity> doctor = doctorRepository.findByUsername(username);
		DoctorDTO dto = doctorMapper.toDto(doctor.get());
		dto.setPassword("");
		return dto;

	}

	// TODO
	public List<DoctorSimpleDTO> getAll(String token) {
		String username = jwt.extractUsername(token);
		Optional<NurseEntity> nurseEntity = nurseRepository.findByUsername(username);
		if (nurseEntity.isEmpty()) {
			throw new ResourceNotFoundException("User: " + username + " is not a nurse!");
		}

		return doctorRepository.findAll().stream().map(entity -> new DoctorSimpleDTO(entity.getId(), entity.getName(),
				entity.getUsername(),
				new SpecializationDTO(entity.getSpecialization().getId(), entity.getSpecialization().getName())))
				.collect(Collectors.toList());
	}

	// TODO da li imati ovo uopste
	public DoctorDTO save(DoctorDTO dto, String token) {
		String username = jwt.extractUsername(token);
		Optional<NurseEntity> nurseEntity = nurseRepository.findByUsername(username);
		if (nurseEntity.isEmpty()) {
			throw new ResourceNotFoundException("User: " + username + " is not a nurse!");
		}

		Optional<DoctorEntity> doctorEntity = doctorRepository.findByUsername(dto.getUsername());
		if (doctorEntity.isPresent()) {
			throw new ResourceAlreadyExistsException(dto.getUsername(), "Doctor with this username already exists.");
		}
		DoctorEntity doctor = doctorRepository.save(doctorMapper.toEntity(dto));
		return doctorMapper.toDto(doctor);
	}

	public void update(UserDTO dto, String token) {
		Optional<DoctorEntity> exisitngDoctor = doctorRepository.findByUsername(jwt.extractUsername(token));
		if (exisitngDoctor.isEmpty()) {
			throw new ResourceNotFoundException("This doctor does't exist");
		}

		DoctorEntity doctor = exisitngDoctor.get();
		doctor.setName(dto.getName());
		doctor.setUsername(dto.getUsername());
//		Password ne bi trebalo da se salje ovde. Treba da odvojimo update sifre, inf o doktoru i bolnica
//		doctor.setPassword(dto.getPassword());

//		doctor.getHospitals().clear();
//		for (HospitalDTO hospital : dto.getHospitals()) {
//			HospitalEntity hospitalEntity = hospitalMapper.toEntity(hospital);
//			doctor.addHospital(hospitalEntity);
//		}

//		doctor.setExaminations(
//				dto.getExaminations().stream().map(examinationMapper::toEntity).collect(Collectors.toSet()));

		doctorRepository.save(doctor);

	}

	// TODO ovo ne bi trebalo da imamo zbog examinationa
	public void delete(Long id) {
		Optional<DoctorEntity> doctorEntity = doctorRepository.findById(id);
		if (doctorEntity.isEmpty()) {
			throw new ResourceNotFoundException("Doctor doesn't exist.");
		}

		doctorRepository.deleteById(id);
	}

	// samo sestra bi trebalo da dodaje i brise examove, mozda izmestiti u nurse
	// service
	public DoctorDTO addExam(Long patientId, Long doctorId, LocalDateTime dateTime, String token) {

		if (dateTime.isBefore(LocalDateTime.now())) {
			throw new InvalidDateExcpetion("Entered date is in past.");
		}

		String username = jwt.extractUsername(token);
		Optional<NurseEntity> nurseEntity = nurseRepository.findByUsername(username);
		if (nurseEntity.isEmpty()) {
			throw new ResourceNotFoundException("User: " + username + " is not a nurse!");
		}

		Optional<DoctorEntity> doctorEntity = doctorRepository.findById(doctorId);
		if (doctorEntity.isEmpty()) {
			throw new ResourceNotFoundException("Doctor doesn't exist.");
		}

		Optional<PatientEntity> patientEntity = patientRepository.findById(patientId);
		if (patientEntity.isEmpty()) {
			throw new ResourceNotFoundException("Patient doesn't exist.");
		}

		DoctorEntity doctor = doctorEntity.get();
		PatientEntity patient = patientEntity.get();

		if (doctor.getExaminations().contains(new ExaminationEntity(doctor, patient, dateTime, ""))) {
			throw new ResourceAlreadyExistsException(null, "This examination already exists.");
		}
		doctor.getExaminations().add(new ExaminationEntity(doctor, patient, dateTime, ""));
		doctor = doctorRepository.save(doctor);
		return doctorMapper.toDto(doctor);

	}

	// TODO proveriti ovo za patient save
	// samo sestra bi trebalo da dodaje i brise examove, mozda izmestiti u nurse
	// service
	public DoctorDTO removeExam(Long patientId, Long doctorId, LocalDateTime dateTime, String token) {

		String username = jwt.extractUsername(token);
		Optional<NurseEntity> nurseEntity = nurseRepository.findByUsername(username);
		if (nurseEntity.isEmpty()) {
			throw new ResourceNotFoundException("User: " + username + " is not a nurse!");
		}

		Optional<DoctorEntity> doctorEntity = doctorRepository.findById(doctorId);
		if (doctorEntity.isEmpty()) {
			throw new ResourceNotFoundException("Doctor doesn't exist.");
		}

		Optional<PatientEntity> patientEntity = patientRepository.findById(patientId);
		if (patientEntity.isEmpty()) {
			throw new ResourceNotFoundException("Patient doesn't exist.");
		}

		DoctorEntity doctor = doctorEntity.get();
		PatientEntity patient = patientEntity.get();
		doctor.removeExamination(patient, dateTime);

//		patientRepository.save(patient);
		doctor = doctorRepository.save(doctor);

		return doctorMapper.toDto(doctor);
	}

}
