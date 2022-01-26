
package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NurseDTO;
import com.example.demo.service.NurseService;

@RestController
@RequestMapping(path = "/nurse")
public class NurseController {
	NurseService nurseService;

	@Autowired
	public NurseController(NurseService nurseService) {
		this.nurseService = nurseService;
	}

	/**
	 * Returns nurse.
	 * 
	 * @param id nurse id
	 */
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Object> getNurse(@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(nurseService.findById(id));
	}

	/**
	 * Returns all existing nurses.
	 */
	@GetMapping
	public @ResponseBody ResponseEntity<List<NurseDTO>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(nurseService.getAll());

	}

	/**
	 * Saves a nurse to the db.
	 * 
	 * @param nurseDTO object containing info about the nurse.
	 */
	@PostMapping
	public @ResponseBody ResponseEntity<Object> save(@RequestBody NurseDTO nurseDTO) {

		return ResponseEntity.status(HttpStatus.OK).body(nurseService.save(nurseDTO));
	}

	/**
	 * Updates the nurse.
	 * 
	 * @param dto object containing info about the nurse
	 */
	@PutMapping
	public @ResponseBody ResponseEntity<Object> update(@RequestBody NurseDTO dto,
			@RequestHeader(name = "Authorization") String token) {
		return ResponseEntity.status(HttpStatus.OK).body(nurseService.update(dto, token));

	}

	/**
	 * Returns nurse from db.
	 * 
	 * @param id nurse id
	 */
	@GetMapping("/profile")
	public @ResponseBody ResponseEntity<Object> getNurse(@RequestHeader(name = "Authorization") String token) {

		return ResponseEntity.status(HttpStatus.OK).body(nurseService.findByUsername(token));
	}

}
