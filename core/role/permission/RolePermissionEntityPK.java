package com.erp.core.role.permission;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RolePermissionEntityPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long roleId;

	private Long permissionId;

}
