package com.example.demo.exception;

import lombok.Getter;

/**
 * Thrown when user wants to register with email that is already present in the
 * database.
 * 
 * @author Uros
 *
 */
@Getter
public class EmailTakenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String userEmail;

	public EmailTakenException(String message) {
		super(message);
	}

	public EmailTakenException(String message, String userEmail) {
		super(message);
		this.userEmail = userEmail;
	}

	public EmailTakenException(String message, String userEmail, Throwable cause) {
		super(message, cause);
		this.userEmail = userEmail;
	}

}