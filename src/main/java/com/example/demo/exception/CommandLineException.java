package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandLineException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String user;

	public CommandLineException(String user, String message) {
		super(message);
		this.user = user;
	}

	public CommandLineException(String user, String message, Throwable cause) {
		super(message, cause);
		this.user = user;
	}

}
