package com.erp.services.employee;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SeparationRequest {

	private Long employeeId;
	private LocalDate separationDate;
	private String separationRemark;
}
