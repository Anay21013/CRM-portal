package com.erp.core.role.permission;

import java.io.Serializable;
import java.util.Collection;

import com.erp.core.base.BaseDTO;
import com.erp.core.permission.Permission;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RolePermission extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long roleId;
	private Long permissionId;
	
	private Collection<Permission> permissions;

	public RolePermission(RolePermissionEntity rolePermissionEntity) {

		this.roleId = rolePermissionEntity.getRoleId();
		this.permissionId = rolePermissionEntity.getPermissionId();
	}

	public RolePermissionEntity populateRoleEntity() {
		RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();

		rolePermissionEntity.setRoleId(roleId);
		rolePermissionEntity.setPermissionId(permissionId);

		return rolePermissionEntity;
	}

}
