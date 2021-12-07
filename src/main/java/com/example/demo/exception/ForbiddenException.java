package com.example.demo.exception;

import lombok.Getter;

/**
 * Thrown when client wants to access admin dashboard
 * 
 * @author Mia Pecelj
 * @author Jovan Popovic
 *
 */
@Getter
public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String userEmail;

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(String message, String userEmail) {
		super(message);
		this.userEmail = userEmail;
	}

	public ForbiddenException(String message, String userEmail, Throwable cause) {
		super(message, cause);
		this.userEmail = userEmail;
	}

}
