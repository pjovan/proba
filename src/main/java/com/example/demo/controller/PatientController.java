package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PatientDTO;
import com.example.demo.service.PatientService;

@RestController
@RequestMapping(path = "/patient")
public class PatientController {

	PatientService patientService;

	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	/**
	 * Gets patient from db.
	 * 
	 * @param id patient id
	 */
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Object> findById(@PathVariable Long id,
			@RequestHeader(name = "Authorization") String token) {

		return ResponseEntity.status(HttpStatus.OK).body(patientService.findById(id, token));
	}

	/**
	 * Returns the list of all saved patients.
	 */
	@GetMapping
	public @ResponseBody ResponseEntity<List<PatientDTO>> getAll(@RequestHeader(name = "Authorization") String token) {
		return ResponseEntity.status(HttpStatus.OK).body(patientService.getAll(token));

	}

	/**
	 * Saves patient in db.
	 * 
	 * @param dto object containing info about patient
	 */
	@PostMapping
	public @ResponseBody ResponseEntity<Object> save(@RequestBody PatientDTO dto,
			@RequestHeader(name = "Authorization") String token) {
		patientService.save(dto, token);
		return ResponseEntity.status(HttpStatus.OK).body("Patient successfully saved.");

	}

	/**
	 * Updates patient in db.
	 * 
	 * @param patient object containing info about patient
	 */
	@PutMapping
	public @ResponseBody ResponseEntity<Object> update(@RequestBody PatientDTO patient,
			@RequestHeader(name = "Authorization") String token) {

		patientService.update(patient, token);
		return ResponseEntity.status(HttpStatus.OK).body("Patient successfully updated.");
	}

	/**
	 * Deletes patient from db.
	 * 
	 * @param id patient id
	 */
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Object> delete(@PathVariable(name = "id") Long id,
			@RequestHeader(name = "Authorization") String token) {
		patientService.delete(id, token);
		return ResponseEntity.status(HttpStatus.OK).body("Patient successfully deleted.");

	}

}
