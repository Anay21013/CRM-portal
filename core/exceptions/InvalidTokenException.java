package com.erp.core.exceptions;

public class InvalidTokenException extends Exception {

	private static final long serialVersionUID = 5986551912763058434L;

	public InvalidTokenException() {
		super();
	}

	public InvalidTokenException(String message) {
		super(message);
	}

	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
	}
}
