package com.erp.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.erp.core.security.payload.request.LoginRequest;
import com.erp.core.security.payload.request.ResetPasswordData;
import com.erp.core.user.IUserService;
import com.erp.core.user.User;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Log4j2
public class SecurityAuthController {

	@Autowired
	private IUserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		log.debug("Authenticating User : "+loginRequest.getUsername());
		try {
			return ResponseEntity.ok(userService.authenticateUser(loginRequest));
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
		
	}
	
	@PostMapping("updatePassword")
	public ResponseEntity<User> updatePassword(@RequestBody ResetPasswordData resetPasswordData) {
		log.debug("Updating password for user with id: {}", resetPasswordData.getUserId());
		try {
			User user = userService.updatePassword(resetPasswordData.getPassword(), resetPasswordData.getUserId());
			log.debug("Password updated successfully for user with id: {}", resetPasswordData.getUserId());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while updating password. " + e.getMessage(), e);
		}
	}
	
	@PostMapping("/resetPasswordCode/{email}")	  
	public ResponseEntity<User> sendResetPasswordCode(@PathVariable("email") String userEmail) {		
		log.debug("Sending reset token to user with email: {}", userEmail);
	 	try {
			User user = userService.sendResetPasswordCode(userEmail);
			log.debug("Token sent successfully to user with email: {}", userEmail);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Sending reset token to user with email: {}", userEmail);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to reset user password: " + e.getMessage(), e);
		}
	}
	
	@PostMapping("resetPassword")
	public ResponseEntity<User> resetPassword(@RequestBody ResetPasswordData resetPasswordData) {
		log.debug("Password reset initiated for user with email: {}", resetPasswordData.getEmail());
		try {
			User user = userService.resetPassword(resetPasswordData);
			log.debug("Password reset successfull for user with email: {}", resetPasswordData.getEmail());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Password reset initiated for user with email: {}", resetPasswordData.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password reset failed for user: " + e.getMessage(), e);
		}
	}
	
}
