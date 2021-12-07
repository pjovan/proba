package com.example.demo.exception;

import lombok.Getter;

/**
 * Thrown when the user attempts an operation he doesn't have permission for.
 * 
 * @author Ivan Kukrkic
 *
 */
@Getter
public class PermissionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String user;

	public PermissionException(String user, String message) {
		super(message);
		this.user = user;
	}

	public PermissionException(String user, String message, Throwable cause) {
		super(message, cause);
		this.user = user;
	}

}
