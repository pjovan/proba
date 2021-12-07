package com.example.demo.exception;

import lombok.Getter;

/**
 * Thrown when the user attempts to change password and is registered via Google
 * or his old password doesn't match.
 * 
 * @author Mia Pecelj
 *
 */
@Getter
public class PasswordChangeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PasswordChangeException(String message) {
		super(message);
	}
}
