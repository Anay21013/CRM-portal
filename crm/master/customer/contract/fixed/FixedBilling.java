package com.erp.crm.master.customer.contract.fixed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
public class FixedBilling extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long contract;
	private Long orgId;
	private String milestoneName;
	private LocalDate milestoneDate;
	private BigDecimal milestoneAmount;
	private String currencyCode;

	public FixedBilling(FixedBillingEntity fixedBillingEntity) {
		this.id = fixedBillingEntity.getId();
		this.orgId = fixedBillingEntity.getOrgId();
		this.contract = fixedBillingEntity.getContract();
		this.milestoneName = fixedBillingEntity.getMilestoneName();
		this.milestoneDate = fixedBillingEntity.getMilestoneDate();
		this.milestoneAmount = fixedBillingEntity.getMilestoneAmount();
		this.currencyCode = fixedBillingEntity.getCurrencyCode();

		super.populateBaseDTO(fixedBillingEntity);
	}

	public FixedBillingEntity populateFixedBillingEntity() {
		FixedBillingEntity fixedBillingEntity = new FixedBillingEntity();
		super.populateBaseEntity(fixedBillingEntity);

		fixedBillingEntity.setId(getId());
		fixedBillingEntity.setOrgId(getOrgId());
		fixedBillingEntity.setContract(getContract());
		fixedBillingEntity.setMilestoneName(getMilestoneName());
		fixedBillingEntity.setMilestoneDate(getMilestoneDate());
		fixedBillingEntity.setMilestoneAmount(getMilestoneAmount());
		fixedBillingEntity.setCurrencyCode(getCurrencyCode());

		return fixedBillingEntity;
	}
}
