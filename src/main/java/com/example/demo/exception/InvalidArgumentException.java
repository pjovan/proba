package com.example.demo.exception;

import lombok.Getter;

/**
 * Thrown when the user provides an invalid argument for any operation.
 * 
 * @author Ivan Kukrkic
 *
 */
@Getter
public class InvalidArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String user;

	public InvalidArgumentException(String user, String message) {
		super(message);
		this.user = user;
	}

	public InvalidArgumentException(String user, String message, Throwable cause) {
		super(message, cause);
		this.user = user;
	}
}
