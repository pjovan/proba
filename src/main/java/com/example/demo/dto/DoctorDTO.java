package com.example.demo.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class DoctorDTO extends UserDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<HospitalDTO> hospitals;
	private Set<ExaminationDTO> examinations;
	private SpecializationDTO specialization;

}
