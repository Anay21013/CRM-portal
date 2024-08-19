package com.erp.core.role;

import java.io.Serializable;
import java.time.LocalDate;
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
public class Role extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long orgId;
	private String name;
	private String description;
	private LocalDate effectiveDate;
	private Role parentRole;
	
	private Collection<Permission> permissions;

	public Role(RoleEntity roleEntity) {

		this.id = roleEntity.getId();
		this.orgId = roleEntity.getOrgId();
		this.name = roleEntity.getName();
		this.description = roleEntity.getDescription();
		this.effectiveDate = roleEntity.getEffectiveDate();
		super.populateBaseDTO(roleEntity);
	}

	public RoleEntity populateRoleEntity() {
		RoleEntity roleEntity = new RoleEntity();
		super.populateBaseEntity(roleEntity);
		roleEntity.setId(getId());
		roleEntity.setOrgId(getOrgId());
		roleEntity.setName(getName());
		roleEntity.setEffectiveDate(getEffectiveDate());
		roleEntity.setDescription(getDescription());
		roleEntity.setParentRole(getParentRole() != null ? getParentRole().getId() : null);
		return roleEntity;
	}

}
