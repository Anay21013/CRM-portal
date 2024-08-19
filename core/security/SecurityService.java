package com.erp.core.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.erp.core.permission.Permission;
import com.erp.core.role.IRoleService;
import com.erp.core.security.user.UserDetailsImpl;

import io.jsonwebtoken.JwtException;

@Component("securityService")
public class SecurityService implements ISecurityService {

//	@Autowired
//	private Environment environment;
	
	@Autowired
	private IRoleService roleService;

	public boolean hasPermission(String permission) {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		List<Permission> permissionList = roleService
				.getAllPermissionForRole(user.getRoleId());
		for (Permission p : permissionList) {
			if (p.getName().equals(permission)) {
				return true;
			}
		}
	        
//		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
//
//		String permissionsForRole = environment.getProperty("ROLEID-"+user.getRoleId().toString());
//		List<String> permissionList = Arrays.asList(permissionsForRole.split(","));
//		for (String p : permissionList) {
//			if (p.equals(permission)) {
//				return true;
//			}
//		}
		throw new JwtException("Access is denied.");

	}
	
	public boolean hasPermission(List<String> permissionListFromController) {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		List<Permission> permissionList = roleService
				.getAllPermissionForRole(user.getRoleId());
		for (Permission p : permissionList) {
			for (String permission : permissionListFromController) {
				if (p.getName().equals(permission)) {
					return true;
				}
			}
		}
		
		throw new JwtException("Access is denied.");
 
	}
}
