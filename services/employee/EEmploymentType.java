package com.erp.services.employee;

public enum EEmploymentType {

	FTE(1), TEMP(2);

	private final int employeeType;

	EEmploymentType(int employeeType) {
		this.employeeType = employeeType;
	}

	public int getValue() {
		return employeeType;
	}
}
