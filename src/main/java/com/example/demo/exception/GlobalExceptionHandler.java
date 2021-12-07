package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(basePackages = "com.example.demo.controller")
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { InvalidTokenException.class })
	public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException ex) {
		log.info("InvalidTokenException: ", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	}

	@ExceptionHandler(value = { InvalidArgumentException.class })
	public ResponseEntity<?> handleInvalidArgumentException(InvalidArgumentException ex) {
		log.info("InvalidArgumentException for user " + ex.getUser() + " : " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(value = { ResourceAlreadyExistsException.class })
	public ResponseEntity<?> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
		log.info("ResourceAlreadyExistsException for user " + ex.getUser() + " : " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
		log.info("ResourceNotFoundException for user " + ex.getUser() + " : " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(value = { FileSystemException.class })
	public ResponseEntity<?> handleSizeExceededException(FileSystemException ex) {
		log.info("FileSystemException for user " + ex.getUser() + " : " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}

	@ExceptionHandler(value = { DatabaseException.class })
	public ResponseEntity<?> handleDatabaseException(DatabaseException ex) {
		log.info("DatabaseException for user " + ex.getUser() + " : " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}

	@ExceptionHandler(value = { PermissionException.class })
	public ResponseEntity<?> handlePermissionException(PermissionException ex) {
		log.info("PermissionException for user " + ex.getUser() + " : " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
	}

	@ExceptionHandler(value = { FunctionExecutionException.class })
	public ResponseEntity<?> handleFunctionExecutionException(FunctionExecutionException ex) {
		log.info("FunctionExecutionException for user " + ex.getUser() + " : " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());
	}

	@ExceptionHandler(value = { InvalidDateExcpetion.class })
	public ResponseEntity<?> handleDateExecutionException(InvalidDateExcpetion ex) {
		log.info("InvalidDateExcpetion: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());
	}

	@ExceptionHandler(value = { EmailSendingException.class })
	public ResponseEntity<?> handleEmailSendingException(EmailSendingException ex) {
		log.info("Error in sending email: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());

	}

	@ExceptionHandler(value = { InterserviceCommunicationException.class })
	public ResponseEntity<?> handleInterserviceCommunicationException(InterserviceCommunicationException ex) {
		log.info("InterserviceCommunicationException for user " + ex.getUser() + " : " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
	}

	@ExceptionHandler(value = { ForbiddenException.class })
	public ResponseEntity<?> handleForbiddenException(ForbiddenException ex) {
		log.info("ForbiddenException: ", ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
	}

}
