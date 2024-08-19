package com.erp.services.employee;

import java.time.LocalDate;
import java.util.List;

public interface IEmployeeService {

	Employee getEmployeeDetailsByUserId(Long id);

	List<Employee> getEmployeeList();

	Employee addEmployeeDetails(Employee employee);

	Employee deleteEmployeeDetails(Long id);

	List<Employee> getEmployeeForReportingEmployeesList(List<Long> reportingToEmployeeList);

	List<Long> getAllChildEmployeeIdListByEmployeeId(Long employeeID);

	Employee getEmployee(Long id, Boolean deleted);

	Employee activateEmployee(Long employeeId);

	Employee deactivateEmployee(Long employeeId);

	Employee intiateEmployeeSeparation(SeparationRequest separationRequest);

	List<Employee> getAllApproversListForOrg(Long orgId);

	Employee addEmployee(Employee employee);

	Employee updateEmployee(Employee employee);

	Employee updateUserIdInEmployee(Employee employee);

	List<Employee> getSelfAndReportingEmployeeList(Long employeeId);

	Employee getBasicEmployeeDetails(Long id);

	Employee findByEmployeeNumber(Integer employeeNumber);

	List<Employee> getAllEmployeeListForOrg(Long orgId);

	List<Employee> getEmployeeListForAdminOrApprover(boolean showSeparated);

	Employee convertTempToFTE(Long employeeId);

	List<Employee> getNonConfirmedInternsByOrgId(Long orgId, Integer employmentType, LocalDate internshipEndDate);
}
