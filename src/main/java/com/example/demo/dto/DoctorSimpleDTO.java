package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSimpleDTO {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String username;
	private SpecializationDTO specialization;
}
