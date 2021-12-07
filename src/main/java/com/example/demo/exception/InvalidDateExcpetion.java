package com.example.demo.exception;

/**
 * Thrown when the parser or anything that has to do with dates cause an error.
 * 
 * @author Pavle Jacovic
 *
 */
public class InvalidDateExcpetion extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidDateExcpetion(String message) {
		super(message);
	}

	public InvalidDateExcpetion(String message, Throwable cause) {
		super(message, cause);
	}
}
