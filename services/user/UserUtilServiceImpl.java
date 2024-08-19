package com.erp.services.user;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.core.exceptions.BusinessException;
import com.erp.core.user.IUserService;
import com.erp.core.user.User;
import com.erp.services.employee.Employee;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class UserUtilServiceImpl implements UserUtilService {

	@Autowired
	private IUserService userService;

	@Override
	@Transactional
	public User inviteAndCreateUser(Employee employeeInvite) {
		try {

			User userRecord = new User();
			userRecord.setEmail(employeeInvite.getEmail());
			userRecord.setEffectiveDate(
					employeeInvite.getJoiningDate() != null ? employeeInvite.getJoiningDate() : LocalDate.now());
			userRecord.setFirstName(employeeInvite.getFirstName());
			userRecord.setLastName(employeeInvite.getLastName());
			userRecord.setOrgId(employeeInvite.getOrgId());
			userRecord.setRoleId(employeeInvite.getRoleId());
			userRecord.setEmployeeId(employeeInvite.getId());
			userRecord.setUnsuccessfulCount(0);
			if(employeeInvite.getUserName() != null) {
				userRecord.setUserName(employeeInvite.getUserName());
			}else {
				userRecord.setUserName(employeeInvite.getEmail().substring(0,employeeInvite.getEmail().indexOf("@")));
			}

			userRecord = userService.addUser(userRecord);
			log.debug("User record created successfully with id - {}. Email invite with code sent to invited user.", userRecord.getId());
			return userRecord;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

	}

	@Override
	public User updateUser(Employee employee) {
		User user = populateUserFromEmployee(employee);
		log.debug("Updating user..");
		try {
			
			user = userService.updateUser(user.getId(), user);
			log.debug("Updating user sucessfully");
		} catch (Exception e) {
			throw new BusinessException("Error while updating user" + e.getMessage());
		}
		return user;
	}

	private User populateUserFromEmployee(Employee employee) {
		User user = new User();
		user.setFirstName(employee.getFirstName());
		user.setLastName(employee.getLastName());
		user.setRoleId(employee.getRoleId());
		user.setId(employee.getUserId());
		return user;
	}

}
