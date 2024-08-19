package com.erp.core.role;

import java.util.List;

import com.erp.core.permission.Permission;
import com.erp.core.role.permission.RolePermission;

public interface IRoleService {
	
	List<Role> getRoleList();

	Role getRole(Long id);
	
	Role getRoleDetail(Long id);
	
	Role getRoleByName(String name);

	Role addRole(Role role);

	Role deleteRole(Long id);

	Role updateRole(Role role);
	
	List<Permission> getAllPermission();
	
	List<Permission> getAllPermissionForRole(Long roleId);
	
	List<String> getAllPermissionNamesForRole(Long roleId);
	
	List<Permission> getAllPermissionForRoleName(String roleName);
	
	List<Permission> getPermissionForRole(Long id);

	List<RolePermission> addRolePermissions(Long roleId, List<Long> permissionIdList);

	RolePermission deleteRolePermission(Long roleId, Long permissionId);
}
