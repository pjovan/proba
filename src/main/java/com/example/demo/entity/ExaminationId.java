package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExaminationId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "doctor_id")
	private Long doctorId;

	@Column(name = "patient_id")
	private Long patientId;

	@Column(name = "date")
	private LocalDateTime dateTime;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		ExaminationId that = (ExaminationId) o;
		return Objects.equals(doctorId, that.doctorId) && Objects.equals(patientId, that.patientId)
				&& Objects.equals(dateTime, that.dateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(doctorId, patientId, dateTime);
	}

	public ExaminationId(Long doctorId, Long patientId) {
		super();
		this.doctorId = doctorId;
		this.patientId = patientId;
	}
}
