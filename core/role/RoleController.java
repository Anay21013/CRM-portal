package com.erp.core.role;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.erp.core.permission.Permission;
import com.erp.core.role.permission.RolePermission;
import com.erp.core.security.user.UserDetailsImpl;

import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/role")
@Log4j2
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("")
	public ResponseEntity<List<Role>> getRoleList() {
		log.debug("Fetching All Roles");
		try {
			List<Role> roleList = roleService.getRoleList();
			log.debug("Fetched All Roles");
			return new ResponseEntity<List<Role>>(roleList, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Fetching All Roles"+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("Role.getRoleList.invalid.id"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Role> getRoleDetail(@PathVariable Long id) {
		log.debug("Fetching Role with role {} ",id);
		try {
			Role role = roleService.getRoleDetail(id);
			log.debug("Fetched Role with role {} ",id);
			return new ResponseEntity<Role>(role, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Fetching Role with role {} ",id+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("Role.getRole.invalid.id"));
		}
	}
	
	@GetMapping("name/{name}")
	public ResponseEntity<Role> getRoleByName(@PathVariable String name) {
		log.debug("Fetching Role with role {} ",name);
		try {
			Role role = roleService.getRoleByName(name);
			log.debug("Fetched Role with role {} ",name);
			return new ResponseEntity<Role>(role, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Fetching Role with role {} ",name+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("Role.getRole.invalid.id"));
			
		}
	}

	@PostMapping("")
	@PreAuthorize("@securityService.hasPermission('addRole')")
	public ResponseEntity<Role> addRole(@RequestBody Role role) {
		log.debug("Adding new Role");
		try {
			Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				throw new JwtException("Authorization can not be empty.");
			}
			UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
			role.setOrgId(user.getOrgId());
			
			role = roleService.addRole(role);
			log.debug("Added new Role with id:{}",role.getId());
			return new ResponseEntity<Role>(role, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding new Role"+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("Role.addRole.invalid.id") + e.getMessage());

		}
	}
	
	@GetMapping("/permissions")
	public ResponseEntity<List<Permission>> getAllPermissions() {
		log.debug("Fetching All Permissions..");
		try {
			List<Permission> allPermissions = roleService.getAllPermission();
			log.debug("Fetched All Permissions..");
			return new ResponseEntity<List<Permission>>(allPermissions, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Fetching All Permissions.."+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while getting all permissions");

		}
	} 
	@GetMapping("/permissions/{id}")
	public ResponseEntity<List<Permission>> getPermissionByRoleId(@PathVariable Long id) {
		log.debug("Fetching All Permissions with role {} ",id);
		try {
			List<Permission> permissions = roleService.getPermissionForRole(id);
			log.debug("Fetched All Permissions with role {} ",id);
			return new ResponseEntity<List<Permission>>(permissions, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Fetching All Permissions with role {} ",id+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while getting permissions for role");

		}
	} 
	
	@GetMapping("all/permissions/{id}")
	public ResponseEntity<List<Permission>> getAllPermissionByRoleId(@PathVariable Long id) {
		log.debug("Fetching all Parent Permissions with role {} ",id);
		try {
			List<Permission> permissions = roleService.getAllPermissionForRole(id);
			log.debug("Fetched all Parent Permissions with role {} ",id);
			return new ResponseEntity<List<Permission>>(permissions, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Fetching all Parent Permissions with role {} ",id+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while getting permissions for role");

		}
	}
	
	@PostMapping("/{roleId}/permission")
	@PreAuthorize("@securityService.hasPermission('addRole')")
	public ResponseEntity<List<RolePermission>> addRolePermission(@PathVariable Long roleId, @RequestParam List<Long> permissionList) {
		log.debug("Adding all Permissions with role id: "+roleId);
		try {
			List<RolePermission> rolePermissions = roleService.addRolePermissions(roleId,permissionList);
			log.debug("Added all Permissions with role id: "+roleId);
			return new ResponseEntity<List<RolePermission>>(rolePermissions, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding all Permissions with role id: "+roleId+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("Role.addRole.invalid.id"));

		}
	}
	
	@DeleteMapping("/{roleId}/permission/{permissionId}")
	@PreAuthorize("@securityService.hasPermission('addRole')")
	public ResponseEntity<RolePermission> deleteRolePermission(@PathVariable Long roleId,@PathVariable Long permissionId) {
		log.debug("Deleting Permission with role id:{} ",roleId+" and Permission Id: {}",permissionId);
		try {
			RolePermission rolePermission = roleService.deleteRolePermission(roleId, permissionId);
			return new ResponseEntity<RolePermission>(rolePermission, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while Deleting Permission with role id:{} ",roleId+" and Permission Id:{} ",permissionId+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("Role.deleteRole.invalid.id"));

		}
	}
	
}