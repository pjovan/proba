package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HospitalDTO;
import com.example.demo.service.HospitalService;

@RestController
@RequestMapping(path = "/hospital")
public class HospitalController {

	private HospitalService hospitalService;

	@Autowired
	public HospitalController(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	/**
	 * Returns a hospital from db.
	 * 
	 * @param id hospital id.
	 */
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Object> findById(@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(hospitalService.findById(id));
	}

	/**
	 * Returns a list of all existing hospitals.
	 */
	@GetMapping
	public @ResponseBody ResponseEntity<List<HospitalDTO>> getAll() throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(hospitalService.getAll());
	}

	/**
	 * Saves a hospital to db.
	 * 
	 * @param dto object containing info about hospital.
	 */
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody HospitalDTO dto) {

		hospitalService.save(dto);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully saved hospital.");
	}

}
