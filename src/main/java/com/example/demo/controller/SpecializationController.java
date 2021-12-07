
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SpecializationDTO;
import com.example.demo.service.SpecializationService;

@RestController
@RequestMapping(path = "/specialization")
public class SpecializationController {
	SpecializationService specializationService;

	@Autowired
	public SpecializationController(SpecializationService specializationService) {
		super();
		this.specializationService = specializationService;
	}

	/**
	 * Saves specialization to the database.
	 * 
	 * @param dto object containing information about the hospital.
	 */
	@PostMapping
	public ResponseEntity<String> save(@RequestBody SpecializationDTO dto,
			@RequestHeader(name = "Authorization") String token) {
		specializationService.save(dto);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully saved specialization.");
	}
}
