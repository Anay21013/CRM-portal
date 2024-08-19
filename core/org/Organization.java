package com.erp.core.org;

import java.io.Serializable;

import com.erp.core.base.BaseDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Organization extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	public Organization(OrganizationEntity organizationEntity) {

		this.id = organizationEntity.getId();
		this.name = organizationEntity.getName();

		super.populateBaseDTO(organizationEntity);
	}

	public OrganizationEntity populateOrganizationEntity() {
		OrganizationEntity organizationEntity = new OrganizationEntity();
		super.populateBaseEntity(organizationEntity);

		organizationEntity.setId(getId());
		organizationEntity.setName(getName());

		return organizationEntity;
	}

}
