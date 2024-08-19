package com.erp.core.exceptions;

public class EmployeeAlreadyExistException extends Exception {
	private static final long serialVersionUID = 1180976705404067258L;

	public EmployeeAlreadyExistException() {
		super();
	}

	public EmployeeAlreadyExistException(String message) {
		super(message);
	}

	public EmployeeAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
