package com.erp.core.exceptions;

public class EmployeeIdNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	public EmployeeIdNotFoundException(String message) {
        super(message);
    }
    public EmployeeIdNotFoundException() {
    }
}
