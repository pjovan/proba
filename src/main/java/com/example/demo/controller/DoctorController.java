package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DoctorDTO;
import com.example.demo.dto.DoctorSimpleDTO;
import com.example.demo.service.DoctorService;

@RestController
@RequestMapping(path = "/doctors")
public class DoctorController {

	DoctorService doctorService;

	@Autowired
	public DoctorController(DoctorService doctorService) {
		super();
		this.doctorService = doctorService;
	}

	/**
	 * Returns doctor from db. RADI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param id doctor id
	 */
	@GetMapping("{username}")
	public @ResponseBody ResponseEntity<Object> getDoctor(@RequestHeader(name = "Authorization") String token,
			@PathVariable String username) {

		return ResponseEntity.status(HttpStatus.OK).body(doctorService.findByUsername(token, username));
	}

	@GetMapping
	public @ResponseBody ResponseEntity<List<DoctorSimpleDTO>> getAll(
			@RequestHeader(name = "Authorization") String token) {
		return ResponseEntity.status(HttpStatus.OK).body(doctorService.getAll(token));

	}

	/**
	 * Updates doctor.
	 * 
	 * @param dto object containing doctor information.
	 */

	// Dodaj validaciju u DTO
	@PutMapping
	public ResponseEntity<Object> update(@RequestBody @Valid DoctorDTO dto,
			@RequestHeader(name = "Authorization") String token, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			bindingResult.getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating doctor " + errors);
		}
		return ResponseEntity.status(HttpStatus.OK).body(doctorService.update(dto, token));
	}

	/**
	 * Saves doctor to db.
	 * 
	 * @param dto object containing doctor information.
	 */
	@PostMapping()
	public ResponseEntity<Object> save(@RequestBody DoctorDTO dto,
			@RequestHeader(name = "Authorization") String token) {

		doctorService.save(dto, token);
		return ResponseEntity.status(HttpStatus.OK).body("Doctor successfully saved!");
	}

	/**
	 * Deletes doctor from db.
	 * 
	 * @param dto
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {

		doctorService.delete(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Doctor successfully deleted!");
	}

	/**
	 * Adds examination to the database.
	 * 
	 * @param patientId
	 * @param doctorId
	 * @param date
	 */
	@PostMapping("/addExam")
	public @ResponseBody ResponseEntity<Object> addExam(@RequestParam Long patientId, @RequestParam Long doctorId,
			@RequestParam Long hospitalId,
			@RequestParam(name = "dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
			@RequestHeader(name = "Authorization") String token) {
		doctorService.addExam(patientId, doctorId, dateTime, token, hospitalId);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully added examination.");

	}

	/**
	 * Removes examination from the database.
	 * 
	 * @param patientId
	 * @param doctorId
	 * @param date
	 */
	@DeleteMapping("/removeExam")
	public @ResponseBody ResponseEntity<Object> removeExam(@RequestParam Long patientId, @RequestParam Long doctorId,
			@RequestParam(name = "dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
			@RequestHeader(name = "Authorization") String token) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(doctorService.removeExam(patientId, doctorId, dateTime, token));

	}

}
