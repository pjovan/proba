package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDTO {

	Long doctorId;
	Long patientId;
	LocalDateTime dateTime;
	LocalDateTime newDateTime;
	String Diagnosis;
}
