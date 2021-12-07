package com.example.demo.exception;

import lombok.Getter;

/**
 * Thrown when the user attempts to access a resource which doesn't exist.
 * 
 * @author Ivan Kukrkic
 *
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String user;

	public ResourceNotFoundException(String user, String message) {
		super(message);
		this.user = user;
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}