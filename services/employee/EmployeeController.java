package com.erp.services.employee;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.erp.core.security.user.UserDetailsImpl;
import com.erp.core.user.User;
import com.erp.services.user.UserUtilService;

import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/employee")
@Log4j2
public class EmployeeController {

	@Autowired
	private Environment environment;

	@Autowired
	private IEmployeeService empService;

	@Autowired
	private UserUtilService userUtilService;

	@GetMapping("{id}")
	@PreAuthorize("@securityService.hasPermission('employeeDetails')")
	public ResponseEntity<Employee> getEmployeeDetails(@PathVariable Long id) {
		log.debug("Fetching employee with user ID {} ", id);
		if (id == null) {
			log.error("Id is null");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("employee.getEmployeeDetails.verification.missing.Id"));

		}
		try {
			Employee employee = empService.getEmployeeDetailsByUserId(id);
			log.debug("Fetched employee with user ID {} ", id);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching employee with user ID {} ", id + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("employee.getEmployeeDetails.Invalid.employeeId") + e.getMessage());
		}
	}

	@GetMapping("{id}/details")
	@PreAuthorize("@securityService.hasPermission('employeeDetails')")
	public ResponseEntity<Employee> getEmployeeDetailsById(@PathVariable Long id) {
		log.debug("Fetching employee with {} ", id);
		if (id == null) {
			log.error("Id is null");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("employee.getEmployeeDetails.verification.missing.Id"));

		}
		try {
			Employee employee = empService.getEmployee(id, Boolean.FALSE);
			log.debug("Fetched employee with {} ", id);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching employee with {} ", id + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("employee.getEmployeeDetails.Invalid.employeeId") + e.getMessage());
		}
	}

	@GetMapping("approver")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<List<Employee>> getApproverList() {
		log.debug("Fetching ALL approvers..");
		try {
			Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				throw new JwtException("Authorization can not be empty.");
			}
			UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
			List<Employee> employee = empService.getAllApproversListForOrg(userDetail.getOrgId());
			log.debug("Fetched ALL approvers..");
			return new ResponseEntity<List<Employee>>(employee, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Fetching ALL approvers.." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("employee.getEmployeeDetails.Invalid.employeeId") + e.getMessage());
		}
	}

	@GetMapping("")
//	@PreAuthorize("@securityService.hasPermission('approverTeam') || @securityService.hasPermission('adminTeam')")
	@PreAuthorize("@securityService.hasPermission({'approverTeam','adminTeam'})")
	public ResponseEntity<List<Employee>> getEmployeeList(@RequestParam(required = false) boolean showSeparated) {
		log.debug("Fetching ALL employee..");
		try {
			List<Employee> employee = empService.getEmployeeListForAdminOrApprover(showSeparated);
			log.debug("Fetched ALL employee..");
			return new ResponseEntity<List<Employee>>(employee, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Fetching ALL employee.." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("employee.getEmployeeDetails.Invalid.employeeId") + e.getMessage());
		}
	}

	// TODO: Remove this
	@GetMapping("employeeBirthday")
	@PreAuthorize("@securityService.hasPermission('getBirthdays')")
	public ResponseEntity<Map<String, String>> getEmployeeBirthday() {
		log.debug("Fetching birthday of employee..");
		try {
			final Map<String, String> birthdayDetails = new HashMap<>();
			log.debug("Fetching all employee..");
			List<Employee> employee = empService.getEmployeeList();
			if (employee != null) {
				employee.forEach(detail -> {
					if (detail.getDob() != null && detail.getDob().getMonth() != null
							&& detail.getDob().getMonth() == LocalDateTime.now().getMonth()) {
						birthdayDetails.putIfAbsent(detail.getFirstName() + ' ' + detail.getLastName(),
								detail.getDob().toString());
					}
				});
			}
			log.debug("Fetched birthday of employee..");
			return new ResponseEntity<Map<String, String>>(birthdayDetails, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Fetching birthday of employee.." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("employee.getEmployeeDetails.Invalid.employeeId") + e.getMessage());
		}
	}

	@PostMapping("")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
		log.debug("Adding employee..");
		try {
			Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				throw new JwtException("Authorization can not be empty.");
			}
			UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
			employee.setOrgId(userDetail.getOrgId());
			employee = empService.addEmployee(employee);
			log.debug("Added employee with id: ", employee.getId());
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding employee." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while Adding employee." + e.getMessage());
		}
	}

	@PostMapping("invite")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<?> inviteEmployee(@Valid @RequestBody Employee employee) {
		log.debug("Adding employee..");
		try {
			Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				throw new JwtException("Authorization can not be empty.");
			}
			UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
			employee.setOrgId(userDetail.getOrgId());

			User user = userUtilService.inviteAndCreateUser(employee);
			employee.setUserId(user.getId());
			empService.updateUserIdInEmployee(employee);

			employee.setUser(user);
			log.debug("Added User for employee with id: ", employee.getId());
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding employee." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while Adding employee." + e.getMessage());
		}
	}

	@PostMapping("{employeeId}/activate")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<Employee> activateEmployee(@PathVariable("employeeId") Long employeeId) {
		log.debug("Activating employee with id: {}", employeeId);
		try {
			Employee employee = empService.activateEmployee(employeeId);
			log.debug("Employee activated with id: {}", employeeId);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while activating employee {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while activating user. " + e.getMessage(),
					e);
		}
	}

	@PostMapping("{employeeId}/deactivate")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<Employee> deactivateEmployee(@PathVariable("employeeId") Long employeeId) {
		log.debug("Deactivating employee with id: {}", employeeId);
		try {
			Employee employee = empService.deactivateEmployee(employeeId);
			log.debug("Employee deactivated with id: {}", employeeId);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while deactivating employee {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while deactivating employee. " + e.getMessage(), e);
		}
	}

	@PostMapping("intiateSeparation")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<Employee> intiateEmployeeSeparation(@RequestBody SeparationRequest separationRequest) {
		log.debug("Initiating seaparation request for employee with id: {}", separationRequest.getEmployeeId());
		try {
			Employee employee = empService.intiateEmployeeSeparation(separationRequest);
			log.debug("Employee separation initiated with id: {}", separationRequest.getEmployeeId());
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while seaparting employee {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while separating employee. " + e.getMessage(), e);
		}
	}

	@PutMapping("/updateEmployeeData")
	@PreAuthorize("@securityService.hasPermission('updateEmployee')")
	public ResponseEntity<Employee> updatemployeeDetails(@RequestBody Employee employee) {
		log.debug("Updating employee details with id: " + employee.getId());
		try {
			Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				throw new JwtException("Authorization can not be empty.");
			}
			UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
			employee.setOrgId(userDetail.getOrgId());
			empService.updateEmployee(employee);
			log.debug("Updating employee sucessfully..");
			log.debug("Updating user ..");
			if (employee.getUserId() != null) {
				userUtilService.updateUser(employee);
			}
			log.debug("Updating user sucessfully..");
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Updaing employee .." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("employee.updateEmployeeDetails.employeeUpdate.Id"));
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<Employee> deletemployeeDetails(@PathVariable Long id) {
		log.debug("Deleting employee details with {} ", id);
		try {
			Employee employee = empService.deleteEmployeeDetails(id);
			log.debug("Deleted employee details with {} ", id);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Deleting employee details with {} ", id + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("employee.deleteEmployeeDetails.employee.message"));
		}
	}

	@DeleteMapping("/convert/{employeeId}")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<Employee> convertTempToFTE(@PathVariable Long employeeId) {
		log.debug("Converting TEMP to FTE for employee id {} ", employeeId);
		try {
			Employee employee = empService.convertTempToFTE(employeeId);
			log.debug("Converting TEMP to FTE for employee id {} ", employeeId);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while converting TEMP to FTE for employee id {}  ", employeeId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while converting TEMP to FTE for employee id " + employeeId + e.getMessage());
		}
	}

}