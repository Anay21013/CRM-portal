package com.erp.services.employee;

public enum EEmployeeGender {

	MALE(1), FEMALE(2), NOT_DISCLOSED(3);

	private final int employeeGender;

	EEmployeeGender(int employeeGender) {
		this.employeeGender = employeeGender;
	}

	public int getValue() {
		return employeeGender;
	}
}
