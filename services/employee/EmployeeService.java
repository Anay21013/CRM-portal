package com.erp.services.employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.core.Constants;
import com.erp.core.exceptions.BusinessException;
import com.erp.core.exceptions.EmployeeIdNotFoundException;
import com.erp.core.role.IRoleService;
import com.erp.core.role.Role;
import com.erp.core.security.user.UserDetailsImpl;
import com.erp.core.user.User;
import com.erp.core.user.UserService;

import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class EmployeeService implements IEmployeeService {

//	@Value("${ses.source}")
//	private String systemEmail;

	@Autowired
	private Environment environment;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private IRoleService roleService;

	@Override
	public Employee addEmployee(Employee employee) {

		if (employee.getEmail() != null) {
			List<EmployeeEntity> empList = employeeRepository.getByEmail(employee.getEmail().toLowerCase());
			if (empList != null && !empList.isEmpty()) {
				throw new BusinessException("Employee already exists with this email - " + employee.getEmail());
			}
		} else {
			throw new BusinessException("Employee email is mandatory to add an employee.");
		}

		if (employee.getType() == null) {
			throw new BusinessException("Employee type is mandatory.");
		}

		EmployeeEntity userEntity = employee.populateEmployeeEntity();
		userEntity.setStatus(EEmployeeStatus.PENDING_ACTIVATION.getValue());
		userEntity.setEmail(employee.getEmail().toLowerCase());
		Employee savedEmployee = new Employee(employeeRepository.save(userEntity));

		return savedEmployee;
	}

	@Override
	public Employee getEmployeeDetailsByUserId(Long id) {
		log.debug("Fetch employee details with {} ", id);
		EmployeeEntity employeeEntity = employeeRepository.findByUserId(id);
		Employee employee = new Employee(employeeEntity);

		if (employeeEntity.getUserId() != null) {
			employee.setUser(userService.getUserDetails(employeeEntity.getUserId()));
		}
		if (employee.getReportingManagerId() != null && employee.getReportingManagerId() != employee.getId()) {
			employee.setReportingManager(getReportingManager(employee.getReportingManagerId()));
		}
		if (employee.getRoleId() != null) {
			Role role = roleService.getRole(employee.getRoleId());
			employee.setRoleName(role.getName());
		}
		return employee;
	}

	@Override
	public Employee getEmployee(Long id, Boolean deleted) {
		log.debug("Fetch employee with {} ", id);

		EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedFlag(id, deleted);
		if (employeeEntity == null) {
			log.error("No Employees found..");
			throw new BusinessException("Employee does not exist.");
		}
		Employee employee = new Employee(employeeEntity);

		if (employeeEntity.getUserId() != null) {
			employee.setUser(userService.getUserDetails(employeeEntity.getUserId()));
		}
		if (employee.getReportingManagerId() != null && employee.getReportingManagerId() != employee.getId()) {
			employee.setReportingManager(getReportingManager(employee.getReportingManagerId()));
		}
		if (employee.getRoleId() != null) {
			Role role = roleService.getRole(employee.getRoleId());
			employee.setRoleName(role.getName());
		}
		return employee;
	}

	private Employee getReportingManager(Long id) {
		log.debug("Fetch Reporting manager with {} ", id);

		Optional<EmployeeEntity> employeeEntityOp = employeeRepository.findById(id);
		if (employeeEntityOp.isEmpty()) {
			log.error("No Reporting manager found..");
			throw new BusinessException("Employee RM does not exist for id " + id);
		}
		Employee employee = new Employee(employeeEntityOp.get());

		return employee;
	}

	@Override
	public List<Employee> getEmployeeList() {
		log.debug("Fetch all employee..");
		Iterable<EmployeeEntity> employeeEntityList = employeeRepository.findAllByDeletedFlag(false);
		List<Employee> empList = new ArrayList<>();
		employeeEntityList.forEach(employeeEntity -> {
			Employee employee = new Employee(employeeEntity);

			if (employeeEntity.getUserId() != null) {
				employee.setUser(userService.getUserDetails(employeeEntity.getUserId()));
			}
			if (employee.getRoleId() != null) {
				Role role = roleService.getRole(employee.getRoleId());
				employee.setRoleName(role.getName());
			}
			empList.add(employee);
		});
		return empList;
	}

	@Override
	public Employee addEmployeeDetails(Employee employee) {
		log.debug("Add Employee with id: {}", employee.getId());
		EmployeeEntity employeeEntity = employee.populateEmployeeEntity();
		employeeEntity = employeeRepository.save(employeeEntity);
		return new Employee(employeeEntity);

	}

	@Override
	public Employee activateEmployee(Long employeeId) {
		Employee employee = null;
		try {
			employee = getEmployee(employeeId, Boolean.FALSE);
		} catch (Exception e) {
			throw new EmployeeIdNotFoundException(environment.getProperty("employee.not.found.error" + employeeId));
		}
		User user = userService.activateUser(employee.getUser().getId());
		employee.setUser(user);
		return employee;
	}

	@Override
	public Employee deactivateEmployee(Long employeeId) {
		Employee employee = null;
		try {
			employee = getEmployee(employeeId, Boolean.FALSE);
		} catch (Exception e) {
			throw new EmployeeIdNotFoundException(environment.getProperty("employee.not.found.error" + employeeId));
		}
		User user = userService.deactivateUser(employee.getUser().getId());
		employee.setUser(user);
		return employee;
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		log.debug("Fetch employee with id: " + employee.getId());
		try {
			EmployeeEntity employeeEntityFromDB = employeeRepository.findById(employee.getId()).get();
			EmployeeEntity employeeEntity = employee.populateEmployeeEntity();
			if (employeeEntityFromDB.getUserId() == null && employee.getUserId() != null) {
				employeeEntityFromDB.setStatus(EEmployeeStatus.ACTIVE.getValue());
				employeeEntityFromDB.setUserId(employee.getUserId());
				employeeEntity = employeeRepository.save(employeeEntityFromDB);
			} else {
				if (!employeeEntity.equals(employeeEntityFromDB)) {
					employeeEntity.setId(employeeEntityFromDB.getId());
					employeeEntity.setCreatedBy(employeeEntityFromDB.getCreatedBy());
					employeeEntity.setCreatedDatetime(employeeEntityFromDB.getCreatedDatetime());

					log.debug("Update employee with id: " + employee.getId());
					employeeEntity = employeeRepository.save(employeeEntity);
				}
			}
			Employee savedEmp = new Employee(employeeEntity);
			//
			//
			return savedEmp;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public Employee deleteEmployeeDetails(Long id) {
		log.debug("Fetch employee with {} ", id);
		EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
		employeeEntity.setDeleted(true);
		log.debug("delete employee with {} ", id);
		employeeEntity = employeeRepository.save(employeeEntity);
		return new Employee(employeeEntity);
	}

	@Override
	public List<Employee> getEmployeeForReportingEmployeesList(List<Long> reportingToEmployeeList) {
		log.debug("Fetch reporting employee list");
		List<EmployeeEntity> employeeEntityList = employeeRepository
				.getEmployeeForReportingEmployeesList(reportingToEmployeeList);
		List<Employee> employeeList = new ArrayList<>();
		for (EmployeeEntity employeeEntity : employeeEntityList) {
			employeeList.add(new Employee(employeeEntity));
		}
		return employeeList;
	}

	// Get All Child employees
	@Override
	public List<Long> getAllChildEmployeeIdListByEmployeeId(Long employeeID) {
		log.debug("Fetch all child employee list");
		Set<Long> finalEmployeeSet = new HashSet<>();
		Set<Long> employeeSet = new HashSet<>();

		employeeSet.add(employeeID);
		finalEmployeeSet.add(employeeID);

		while (!employeeSet.isEmpty()) {
			List<Employee> childEmployeeList = getEmployeeForReportingEmployeesList(employeeSet.stream().toList());
			List<Long> childEmployeeIdList = new ArrayList<>();
			for (Employee employee : childEmployeeList) {
				childEmployeeIdList.add(employee.getId());
			}
			employeeSet.clear();
			employeeSet.addAll(childEmployeeIdList);
			finalEmployeeSet.addAll(childEmployeeIdList);
		}

		return finalEmployeeSet.stream().toList();
	}

	@Override
	public List<Employee> getAllApproversListForOrg(Long orgId) {

		List<Employee> approverList = new ArrayList<>();

		employeeRepository.getByApproverTrueAndOrgIdOrderByEmployeeNumberDesc(orgId)
				.forEach(approveryEntity -> approverList.add(new Employee(approveryEntity)));

		return approverList;
	}

	@Override
	public List<Employee> getSelfAndReportingEmployeeList(Long employeeId) {
		log.debug("Fetch self and reporting employee..");
		Iterable<EmployeeEntity> employeeEntityList = employeeRepository
				.getByIdOrReportingManagerAndDeletedFalseOrderByEmployeeNumberDesc(employeeId, employeeId);
		List<Employee> empList = new ArrayList<>();
		employeeEntityList.forEach(employeeEntity -> {
			Employee employee = new Employee(employeeEntity);
			if (employeeEntity.getUserId() != null) {
				employee.setUser(userService.getUserDetails(employeeEntity.getUserId()));
			}
			if (employee.getRoleId() != null) {
				Role role = roleService.getRole(employee.getRoleId());
				employee.setRoleName(role.getName());
			}
			empList.add(employee);
		});
		return empList;
	}

	@Override
	public Employee getBasicEmployeeDetails(Long id) {
		Optional<EmployeeEntity> empEntityOp = employeeRepository.findById(id);

		if (empEntityOp.isEmpty()) {
			throw new BusinessException("Employee not found for given id - " + id);
		}

		return new Employee(empEntityOp.get());
	}

	@Override
	public Employee findByEmployeeNumber(Integer employeeNumber) {
		List<EmployeeEntity> empEntityList = employeeRepository.findByEmployeeNumber(employeeNumber);

		if (empEntityList.isEmpty()) {
//			throw new BusinessException("Employee not found for given employee number - "+employeeNumber);
			return new Employee();
		}

		return new Employee(empEntityList.get(0));
	}

	@Override
	public List<Employee> getAllEmployeeListForOrg(Long orgId) {

		List<Employee> employeeList = new ArrayList<>();

		employeeRepository.getByDeletedFalseAndOrgIdOrderByEmployeeNumberDesc(orgId).forEach(employeeEntity -> {
			Employee emp = new Employee(employeeEntity);
			if (employeeEntity.getReportingManager() != null) {
				emp.setReportingManager(getBasicEmployeeDetails(employeeEntity.getReportingManager()));
			}
			employeeList.add(emp);
		});

		return employeeList;
	}

	@Override
	public List<Employee> getEmployeeListForAdminOrApprover(boolean showSeparated) {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		List<String> permissions = userDetail.getPermissionNamesList();

		List<Employee> employeeList = new ArrayList<>();
		if (permissions.contains("adminTeam")) {
			employeeRepository.getEmployeeList(null).forEach(employeeEntity -> {

				if (showSeparated) {
					Employee emp = new Employee(employeeEntity);
					if (emp.getStatus().equals(EEmployeeStatus.SEPARATED.getValue())) {
						if (employeeEntity.getReportingManager() != null) {
							emp.setReportingManager(getBasicEmployeeDetails(employeeEntity.getReportingManager()));
						}
						employeeList.add(emp);
					}
				} else {
					Employee emp = new Employee(employeeEntity);
					if (!emp.getStatus().equals(EEmployeeStatus.SEPARATED.getValue())) {
						if (employeeEntity.getReportingManager() != null) {
							emp.setReportingManager(getBasicEmployeeDetails(employeeEntity.getReportingManager()));
						}
						employeeList.add(emp);
					}
				}
			});
		} else if (permissions.contains("approverTeam")) {
			employeeRepository.getEmployeeList(userDetail.getEmployeeId()).forEach(employeeEntity -> {
				if (showSeparated) {
					Employee emp = new Employee(employeeEntity);
					if (emp.getStatus().equals(EEmployeeStatus.SEPARATED.getValue())) {
						if (employeeEntity.getReportingManager() != null) {
							emp.setReportingManager(getBasicEmployeeDetails(employeeEntity.getReportingManager()));
						}
						employeeList.add(emp);
					}
				} else {
					Employee emp = new Employee(employeeEntity);
					if (!emp.getStatus().equals(EEmployeeStatus.SEPARATED.getValue())) {
						if (employeeEntity.getReportingManager() != null) {
							emp.setReportingManager(getBasicEmployeeDetails(employeeEntity.getReportingManager()));
						}
						employeeList.add(emp);
					}
				}
			});
		}
		return employeeList;
	}

	@Override
	public Employee intiateEmployeeSeparation(SeparationRequest separationRequest) {
		log.debug("Fetch employee with id: " + separationRequest.getEmployeeId());
		try {
			EmployeeEntity employeeEntityFromDB = employeeRepository.findById(separationRequest.getEmployeeId()).get();
			employeeEntityFromDB.setEndDate(separationRequest.getSeparationDate());
			employeeEntityFromDB.setSeparated(Boolean.TRUE);
			employeeEntityFromDB.setSeparationRemark(separationRequest.getSeparationRemark());
			employeeEntityFromDB.setSeparationDate(separationRequest.getSeparationDate());
			if (separationRequest.getSeparationDate() != null
					&& separationRequest.getSeparationDate().isBefore(LocalDate.now())) {
				employeeEntityFromDB.setStatus(EEmployeeStatus.SEPARATED.getValue());
			} else {
				employeeEntityFromDB.setStatus(EEmployeeStatus.ON_SEPARATION.getValue());
			}
			employeeEntityFromDB = employeeRepository.save(employeeEntityFromDB);
			log.debug("Separation initiated for employee with id: " + employeeEntityFromDB.getId());
			return new Employee(employeeEntityFromDB);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public Employee convertTempToFTE(Long employeeId) {
		log.debug("Fetch employee with {} ", employeeId);

		EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedFlag(employeeId, false);
		if (employeeEntity == null) {
			log.error("No Employees found..");
			throw new BusinessException("Employee does not exist.");
		}
		Employee existingTempEmployeeFromDB = new Employee(employeeEntity);

		Integer lastEmpId = employeeRepository.latestEmployeeNumberByEmploymentType(EEmploymentType.FTE.getValue());
		if (lastEmpId == null) {
			lastEmpId = 100000;
		} else {
			lastEmpId = lastEmpId + 1;
		}

		Employee newlyConvertedEmployee = new Employee();
		newlyConvertedEmployee = newlyConvertedEmployee.copyEmployee(existingTempEmployeeFromDB);
		newlyConvertedEmployee.setEmployeeNumber(lastEmpId);
		newlyConvertedEmployee.setStatus(EEmployeeStatus.ACTIVE.getValue());
		newlyConvertedEmployee.setType(EEmploymentType.FTE.getValue());
		newlyConvertedEmployee.setStartDate(LocalDate.now());
		newlyConvertedEmployee.setJoiningDate(LocalDate.now());

		newlyConvertedEmployee.setConfirmationDate(LocalDate.now());
		newlyConvertedEmployee.setConfirmed(Boolean.TRUE);
		newlyConvertedEmployee.setSeparationDate(null);
		newlyConvertedEmployee.setSeparated(Boolean.FALSE);
		newlyConvertedEmployee.setSeparationRemark(null);

		Role role = roleService.getRoleByName(Constants.FTE_ROLE);
		newlyConvertedEmployee.setRoleId(role.getId());

		// Save newly created employee
		EmployeeEntity newlyCreatedEmployeeEntity = employeeRepository
				.save(newlyConvertedEmployee.populateEmployeeEntity());

		// Update user with new employee id
		User user = userService.updateEmployeeIdInUser(newlyCreatedEmployeeEntity.getUserId(),
				newlyCreatedEmployeeEntity.getId(), newlyCreatedEmployeeEntity.getRoleId());
		log.debug("Employee Id updated for user {} with new employeeId {} and roleId {}", user.getId(),
				user.getEmployeeId(), user.getRoleId());

		// Separate the existing employee
		employeeEntity.setEndDate(LocalDate.now().minusDays(1));
		employeeEntity.setSeparated(Boolean.TRUE);
		employeeEntity.setSeparationRemark("Converted Temp to FTE");
		employeeEntity.setSeparationDate(LocalDate.now().minusDays(1));
		employeeEntity.setStatus(EEmployeeStatus.SEPARATED.getValue());
		employeeEntity = employeeRepository.save(employeeEntity);
		log.debug("Separation initiated for employee with id: " + employeeEntity.getId());

		EmployeeEntity approverEntity = employeeRepository
				.findByIdAndDeletedFlag(newlyCreatedEmployeeEntity.getReportingManager(), false);
		if (approverEntity == null) {
			log.error("No Approver found..");
			throw new BusinessException("Approver does not exist.");
		}

		return new Employee(newlyCreatedEmployeeEntity);
	}

	@Override
	public List<Employee> getNonConfirmedInternsByOrgId(Long orgId, Integer employmentType,
			LocalDate internshipEndDate) {
		List<Employee> internList = new ArrayList<>();
		employeeRepository.getInternList(orgId, employmentType, internshipEndDate)
				.forEach(internE -> internList.add(new Employee(internE)));
		return internList;
	}

	@Override
	public Employee updateUserIdInEmployee(Employee employee) {
		log.debug("Fetch employee with id: " + employee.getId());
		try {
			EmployeeEntity employeeEntityFromDB = employeeRepository.findById(employee.getId()).get();
			employeeEntityFromDB.setStatus(EEmployeeStatus.ACTIVE.getValue());
			employeeEntityFromDB.setUserId(employee.getUserId());
			employeeEntityFromDB = employeeRepository.save(employeeEntityFromDB);

			return employee;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

}