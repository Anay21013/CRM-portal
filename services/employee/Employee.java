package com.erp.services.employee;

import java.io.Serializable;
import java.time.LocalDate;

import com.erp.core.base.BaseDTO;
import com.erp.core.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class Employee extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long orgId;
	private Long userId;
	private String userName;
	private Integer employeeNumber;
	private Integer type;
	private String prefix;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate startDate;
	private Long reportingManagerId;
	private Boolean approver;
	private Integer status;
	private LocalDate dob;
	private LocalDate joiningDate;
	
	private String email;
	private String alternateEmail;
	private String homePhone;
	private String workPhone;
	private String addressLine1;
	private String addressLine2;
	private String addressCity;
	private String addressState;
	private String addressZip;
	private String addressCountry;
	private LocalDate internshipEndDate;
	private LocalDate confirmationDate;
	private Boolean confirmed;
	
	private LocalDate separationDate;
	private Boolean separated;
	private String separationRemark;
	private Integer gender;
	
	private Employee reportingManager;
	private Long assetId;
	private Long roleId;
	private String roleName;
	private User user;
	
	private Integer jobTitle;
	private Integer jobLevel;
	private Integer jobBand;
	private Integer jobLocation;
	private String skills;
	
	public Employee(EmployeeEntity employeeEntity) {

		this.id = employeeEntity.getId();
		this.orgId = employeeEntity.getOrgId();
		this.employeeNumber = employeeEntity.getEmployeeNumber();
		this.approver = employeeEntity.getApprover();
		this.userId = employeeEntity.getUserId();
		this.status = employeeEntity.getStatus();
		this.type = employeeEntity.getType();
		this.prefix = employeeEntity.getPrefix();
		this.firstName = employeeEntity.getFirstName();
		this.middleName = employeeEntity.getMiddleName();
		this.lastName = employeeEntity.getLastName();
		this.startDate = employeeEntity.getStartDate();
		this.dob = employeeEntity.getDob();
		this.joiningDate = employeeEntity.getJoiningDate();
		this.reportingManagerId = employeeEntity.getReportingManager();
		this.roleId = employeeEntity.getRoleId();
		
		this.email = employeeEntity.getEmail();
		this.alternateEmail = employeeEntity.getAlternateEmail();
		this.homePhone = employeeEntity.getHomePhone();
		this.workPhone = employeeEntity.getWorkPhone();
		this.addressLine1 = employeeEntity.getAddressLine1();
		this.addressLine2 = employeeEntity.getAddressLine2();
		this.addressCity = employeeEntity.getAddressCity();
		this.addressZip= employeeEntity.getAddressZip();
		this.addressState = employeeEntity.getAddressState();
		this.addressCountry = employeeEntity.getAddressCountry();
		this.internshipEndDate = employeeEntity.getInternshipEndDate();
		this.confirmationDate = employeeEntity.getConfirmationDate();
		this.confirmed = employeeEntity.getConfirmed();
		this.separationDate = employeeEntity.getSeparationDate();
		this.separated = employeeEntity.getSeparated();
		this.separationRemark = employeeEntity.getSeparationRemark();
		this.gender = employeeEntity.getGender();
		
		super.populateBaseDTO(employeeEntity);
	}

	public EmployeeEntity populateEmployeeEntity() {
		EmployeeEntity employeeEntity = new EmployeeEntity();
		super.populateBaseEntity(employeeEntity);
		employeeEntity.setId(getId());
		employeeEntity.setOrgId(getOrgId());
		employeeEntity.setUserId(getUserId());
		employeeEntity.setEmployeeNumber(getEmployeeNumber());
		employeeEntity.setStatus(getStatus());
		employeeEntity.setType(getType());
		employeeEntity.setPrefix(getPrefix());
		employeeEntity.setFirstName(getFirstName());
		employeeEntity.setMiddleName(getMiddleName());
		employeeEntity.setLastName(getLastName());
		employeeEntity.setStartDate(getStartDate());
		employeeEntity.setDob(getDob());
		employeeEntity.setJoiningDate(getJoiningDate());
		employeeEntity.setReportingManager(getReportingManagerId());
		employeeEntity.setApprover(getApprover());
		employeeEntity.setRoleId(getRoleId());
		
		employeeEntity.setEmail(getEmail());
		employeeEntity.setAlternateEmail(getAlternateEmail());
		employeeEntity.setHomePhone(getHomePhone());
		employeeEntity.setWorkPhone(getWorkPhone());
		employeeEntity.setAddressLine1(getAddressLine1());
		employeeEntity.setAddressLine2(getAddressLine2());
		employeeEntity.setAddressCity(getAddressCity());
		employeeEntity.setAddressZip(getAddressZip());
		employeeEntity.setAddressState(getAddressState());
		employeeEntity.setAddressCountry(getAddressCountry());
		employeeEntity.setInternshipEndDate(getInternshipEndDate());
		employeeEntity.setConfirmationDate(getConfirmationDate());
		employeeEntity.setConfirmed(getConfirmed());
		employeeEntity.setSeparationDate(getSeparationDate());
		employeeEntity.setSeparated(getSeparated());
		employeeEntity.setSeparationRemark(getSeparationRemark());
		
		employeeEntity.setGender(getGender());
		return employeeEntity;
	}
	
	public Employee copyEmployee(Employee copyFromEmployee) {
		Employee newEmployee = new Employee();
		
		newEmployee.setOrgId(copyFromEmployee.getOrgId());
		newEmployee.setUserId(copyFromEmployee.getUserId());
		newEmployee.setEmployeeNumber(copyFromEmployee.getEmployeeNumber());
		newEmployee.setStatus(copyFromEmployee.getStatus());
		newEmployee.setType(copyFromEmployee.getType());
		newEmployee.setPrefix(copyFromEmployee.getPrefix());
		newEmployee.setFirstName(copyFromEmployee.getFirstName());
		newEmployee.setMiddleName(copyFromEmployee.getMiddleName());
		newEmployee.setLastName(copyFromEmployee.getLastName());
		newEmployee.setStartDate(copyFromEmployee.getStartDate());
		newEmployee.setDob(copyFromEmployee.getDob());
		newEmployee.setJoiningDate(copyFromEmployee.getJoiningDate());
		newEmployee.setReportingManagerId(copyFromEmployee.getReportingManagerId());
		newEmployee.setApprover(copyFromEmployee.getApprover());
		newEmployee.setRoleId(copyFromEmployee.getRoleId());
		
		newEmployee.setEmail(copyFromEmployee.getEmail());
		newEmployee.setAlternateEmail(copyFromEmployee.getAlternateEmail());
		newEmployee.setHomePhone(copyFromEmployee.getHomePhone());
		newEmployee.setWorkPhone(copyFromEmployee.getWorkPhone());
		newEmployee.setAddressLine1(copyFromEmployee.getAddressLine1());
		newEmployee.setAddressLine2(copyFromEmployee.getAddressLine2());
		newEmployee.setAddressCity(copyFromEmployee.getAddressCity());
		newEmployee.setAddressZip(copyFromEmployee.getAddressZip());
		newEmployee.setAddressState(copyFromEmployee.getAddressState());
		newEmployee.setAddressCountry(copyFromEmployee.getAddressCountry());
		newEmployee.setInternshipEndDate(copyFromEmployee.getInternshipEndDate());
		newEmployee.setConfirmationDate(copyFromEmployee.getConfirmationDate());
		newEmployee.setConfirmed(copyFromEmployee.getConfirmed());
		newEmployee.setSeparationDate(copyFromEmployee.getSeparationDate());
		newEmployee.setSeparated(copyFromEmployee.getSeparated());
		newEmployee.setSeparationRemark(copyFromEmployee.getSeparationRemark());
		
		newEmployee.setGender(copyFromEmployee.getGender());
		return newEmployee;
	}

}
