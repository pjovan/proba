package com.example.demo.exception;

/**
 * Thrown when the token provided can't be parsed.
 * 
 * @author Ivan Kukrkic
 *
 */
public class InvalidTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidTokenException(String message) {
		super(message);
	}

	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
	}
}
