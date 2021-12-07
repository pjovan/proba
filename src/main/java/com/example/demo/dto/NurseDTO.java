package com.example.demo.dto;

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
public class NurseDTO extends UserDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HospitalDTO hospital;
}
