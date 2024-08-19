package com.erp.core.role.permission;
 
import java.io.Serializable;

import com.erp.core.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
 
@Entity
@Table(name = "roles_permissions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RolePermissionEntityPK.class)
public class RolePermissionEntity extends BaseEntity implements Serializable {
 
	private static final long serialVersionUID = 1L;
	@Id
	@NotNull
	@Column(name = "role_id")
	private Long roleId;
	
	@Id
	@NotNull
	@Column(name = "permission_id")
	private Long permissionId;
}
 