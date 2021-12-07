package com.example.demo.exception;

import lombok.Getter;

/**
 * Thrown when the user attempt to add a resource to the server which already
 * exists
 * 
 * @author Ivan Kukrkic
 *
 */
@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String user;

	public ResourceAlreadyExistsException(String user, String message) {
		super(message);
		this.user = user;
	}

	public ResourceAlreadyExistsException(String user, String message, Throwable cause) {
		super(message, cause);
		this.user = user;
	}

}
