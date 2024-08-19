package com.erp.services.user;
 
import com.erp.core.user.User;
import com.erp.services.employee.Employee;
 
public interface UserUtilService {
 
	User inviteAndCreateUser(final Employee signupRequest);
 
	User updateUser(final Employee employee);

}
 