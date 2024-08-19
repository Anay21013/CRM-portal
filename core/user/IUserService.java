package com.erp.core.user;
 
import java.util.List;

import com.erp.core.security.payload.request.LoginRequest;
import com.erp.core.security.payload.request.ResetPasswordData;
import com.erp.core.security.payload.response.JwtResponse;
 
public interface IUserService {
//	SecurityAuthController methods
	JwtResponse authenticateUser(LoginRequest loginRequest);
	User updatePassword(final String password, final Long Id);
 	User sendResetPasswordCode(String email);
 	User resetPassword(ResetPasswordData resetPasswordData);
	
// 	UserController methods
 	List<User> getAllUsers(Integer roleValue);
 	User getUserDetails(Long Id);
 	User addUser(User user);
 	User updateUser(Long id,User user);
 	User deleteUser(Long id);
	
	boolean checkIfUserExist(final String email);
	User getUserByUserId(Long userId);
	
	User deactivateUser(Long id);
	User activateUser(Long id);
	
	User updateEmployeeIdInUser(Long userId, Long newEmployeeId, Long roleId);
}
 