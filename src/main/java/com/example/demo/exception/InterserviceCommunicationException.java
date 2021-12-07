package com.example.demo.exception;

import lombok.Getter;

/**
 * Thrown when communication between services fails.
 * 
 * @author Mia Pecelj
 * @author Jovan Popovic
 *
 */
@Getter
public class InterserviceCommunicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String user;

	public InterserviceCommunicationException(String message) {
		super(message);
	}

	public InterserviceCommunicationException(String user, String message) {
		super(message);
		this.user = user;
	}

	public InterserviceCommunicationException(String user, String message, Throwable cause) {
		super(message, cause);
		this.user = user;
	}
}
