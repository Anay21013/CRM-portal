package com.erp.services.employee;

public enum EEmployeeStatus {

	PENDING_ACTIVATION(1), ACTIVE(2), SEPARATED(3), ON_SEPARATION(4);

	private final int employeeStatus;

	EEmployeeStatus(int employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public int getValue() {
		return employeeStatus;
	}
}
