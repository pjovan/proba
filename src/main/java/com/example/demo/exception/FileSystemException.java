package com.example.demo.exception;

import lombok.Getter;

/**
 * Thrown when an IOException related to the file system occurs.
 * 
 * @author Ivan Kukrkic
 *
 */
@Getter
public class FileSystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String user;

	public FileSystemException(String user, String message) {
		super(message);
		this.user = user;
	}

	public FileSystemException(String user, String message, Throwable cause) {
		super(message, cause);
		this.user = user;
	}
}
