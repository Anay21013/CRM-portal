package com.erp.core.exceptions;

/**
 * Exception thrown by system in case some one try to register with already
 * exisiting email id in the system.
 */
public class UserAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1180976705404067258L;

	public UserAlreadyExistException() {
		super();
	}

	public UserAlreadyExistException(String message) {
		super(message);
	}

	public UserAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
