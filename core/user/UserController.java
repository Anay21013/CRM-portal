package com.erp.core.user;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
 
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user") 
@Log4j2
public class UserController {
	@Autowired
	private IUserService userService;
	
	@GetMapping
//	@PreAuthorize("@securityService.hasPermission('viewUserDashboard')")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) Integer roleValue) {
		log.debug("Fetching all users");
		try {
			List<User> users = userService.getAllUsers(roleValue);
			return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error("Error while fetching all users " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to find users.");
		}
	}
	
	@GetMapping("/{id}")
//	@PreAuthorize("@securityService.hasPermission('viewUserDashboard')")
	public ResponseEntity<User> getUserDetails(@PathVariable Long id) {
		log.debug("Fetching user with {} ",id);
		try {
			User user= userService.getUserDetails(id);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error("Error while fetching user " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to find user.");
		}
	}
	
	@PostMapping("")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		log.debug("Adding new user");
		try {
			Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				throw new JwtException("Authorization can not be empty.");
			}
			UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
			user.setOrgId(userDetail.getOrgId());
			
			user = userService.addUser(user);
			log.debug("Added new user with id:{}",user.getId());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding new user"+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());

		}
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
		log.debug("Updating User..");
		try {
			User updatedUser = userService.updateUser(id,user);
			return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error("Error while updating user " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to Update User.");
		}		
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("@securityService.hasPermission('addEmployee')")
	public ResponseEntity<User> deleteUser(@PathVariable Long id){
		log.debug("Deleting User..");
		try {
			User updatedUser = userService.deleteUser(id);
			return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error("Error while deleting user " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to Delete User.");
		}		
	}
}