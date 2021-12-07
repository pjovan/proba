package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "examination")
public class ExaminationEntity {

	@EmbeddedId
	private ExaminationId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("patientId")
	@JoinColumn(name = "patient_id")
	private PatientEntity patient;

	@ManyToOne
	@MapsId("doctorId")
	@JoinColumn(name = "doctor_id")
	private DoctorEntity doctor;

	private String diagnosis;

	public ExaminationEntity(DoctorEntity doctor, PatientEntity patient, LocalDateTime dateTime, String diagnosis) {
		super();
		this.id = new ExaminationId(doctor.getId(), patient.getId(), dateTime);
		this.patient = patient;
		this.doctor = doctor;
		this.diagnosis = diagnosis;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		ExaminationEntity that = (ExaminationEntity) o;
		return (patient.equals(that.patient) && doctor.equals(that.doctor)
				&& id.getDateTime().equals(that.getId().getDateTime()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "ExaminationEntity [id=" + id + ", patient=" + patient + ", doctor=" + doctor + ", diagnosis="
				+ diagnosis + "]";
	}

}
