package com.example.demo.exception;

public class EmailSendingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmailSendingException(String message) {
		super(message);
	}

}
