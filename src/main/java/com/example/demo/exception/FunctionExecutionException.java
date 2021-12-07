package com.example.demo.exception;

import lombok.Getter;

/**
 * Thrown when user functions don't execute correctly.
 * 
 * @author Ivan Kukrkic
 *
 */
@Getter
public class FunctionExecutionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String user;

	public FunctionExecutionException(String user, String message) {
		super(message);
		this.user = user;
	}

	public FunctionExecutionException(String user, String message, Throwable cause) {
		super(message, cause);
		this.user = user;
	}
}
