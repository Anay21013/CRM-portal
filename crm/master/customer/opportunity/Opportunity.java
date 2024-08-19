package com.erp.crm.master.customer.opportunity;

import java.io.Serializable;
import java.time.LocalDate;

import com.erp.core.base.BaseDTO;
import com.erp.crm.master.customer.Customer;

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
public class Opportunity extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long customer;
	private Long orgId;
	private String name;
	private LocalDate receivedDate;
	private Long owner;
	private Integer type;
	private Boolean won;
	private Boolean lost;

	private Customer customerObject;

	public Opportunity(OpportunityEntity opportunityEntity) {
		this.id = opportunityEntity.getId();
		this.orgId = opportunityEntity.getOrgId();
		this.customer = opportunityEntity.getCustomer();
		this.name = opportunityEntity.getName();
		this.receivedDate = opportunityEntity.getReceivedDate();
		this.owner = opportunityEntity.getOwner();
		this.type = opportunityEntity.getType();
		this.won = opportunityEntity.getWon();
		this.lost = opportunityEntity.getLost();

		super.populateBaseDTO(opportunityEntity);
	}

	public OpportunityEntity populateOpportunityEntity() {
		OpportunityEntity opportunityEntity = new OpportunityEntity();
		super.populateBaseEntity(opportunityEntity);

		opportunityEntity.setId(getId());
		opportunityEntity.setOrgId(getOrgId());
		opportunityEntity.setCustomer(getCustomer());
		opportunityEntity.setName(getName());
		opportunityEntity.setReceivedDate(getReceivedDate());
		opportunityEntity.setOwner(getOwner());
		opportunityEntity.setType(getType());
		opportunityEntity.setWon(getWon());
		opportunityEntity.setLost(getLost());

		return opportunityEntity;
	}
}
