package com.erp.core.permission;

import java.io.Serializable;
import java.time.LocalDate;

import com.erp.core.base.BaseDTO;

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
public class Permission extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long orgId;
	private String name;
	private String description;
	private String groupName;
	private LocalDate effectiveDate;

	public Permission(PermissionEntity permissionEntity) {

		this.id = permissionEntity.getId();
		this.orgId = permissionEntity.getOrgId();
		this.name = permissionEntity.getName();
		this.description = permissionEntity.getDescription();
		this.groupName = permissionEntity.getGroupName();
		this.effectiveDate = permissionEntity.getEffectiveDate();
		super.populateBaseDTO(permissionEntity);
	}

	public PermissionEntity populatePermissionEntity() {
		PermissionEntity permissionEntity = new PermissionEntity();
		super.populateBaseEntity(permissionEntity);

		permissionEntity.setId(getId());
		permissionEntity.setOrgId(getOrgId());
		permissionEntity.setName(getName());
		permissionEntity.setEffectiveDate(getEffectiveDate());
		permissionEntity.setGroupName(getGroupName());
		permissionEntity.setDescription(getDescription());

		return permissionEntity;
	}
}
