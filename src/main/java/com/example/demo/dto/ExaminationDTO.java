package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.demo.entity.ExaminationId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ExaminationId id;
	private DoctorSimpleDTO doctor;
	private PatientSimpleDTO patient;
	private String diagnosis;
	private HospitalDTO hospital;

	public ExaminationDTO(DoctorSimpleDTO doctor, PatientSimpleDTO patient, LocalDateTime dateTime, String diagnosis,
			HospitalDTO hospital) {
		super();
		this.id = new ExaminationId(doctor.getId(), patient.getId(), dateTime);
		this.diagnosis = diagnosis;
		this.doctor = doctor;
		this.patient = patient;
		this.hospital = hospital;
	}

}
