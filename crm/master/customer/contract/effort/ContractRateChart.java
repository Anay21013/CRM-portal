package com.erp.crm.master.customer.contract.effort;

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
public class ContractRateChart extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long contract;
	private Long orgId;
	private String roleName;
	private BigDecimal roleRate;
	private Integer roleRateFrequency;
	private String currencyCode;
	private LocalDate startDate;

	public ContractRateChart(ContractRateChartEntity effortBasedBillingEntity) {
		this.id = effortBasedBillingEntity.getId();
		this.orgId = effortBasedBillingEntity.getOrgId();
		this.contract = effortBasedBillingEntity.getContract();
		this.roleName = effortBasedBillingEntity.getRoleName();
		this.roleRate = effortBasedBillingEntity.getRoleRate();
		this.roleRateFrequency = effortBasedBillingEntity.getRoleRateFrequency();
		this.currencyCode = effortBasedBillingEntity.getCurrencyCode();
		this.startDate = effortBasedBillingEntity.getStartDate();

		super.populateBaseDTO(effortBasedBillingEntity);
	}

	public ContractRateChartEntity populateEffortBasedBillingEntity() {
		ContractRateChartEntity effortBasedBillingEntity = new ContractRateChartEntity();
		super.populateBaseEntity(effortBasedBillingEntity);

		effortBasedBillingEntity.setId(getId());
		effortBasedBillingEntity.setOrgId(getOrgId());
		effortBasedBillingEntity.setContract(getContract());
		effortBasedBillingEntity.setRoleName(getRoleName());
		effortBasedBillingEntity.setRoleRate(getRoleRate());
		effortBasedBillingEntity.setRoleRateFrequency(getRoleRateFrequency());
		effortBasedBillingEntity.setCurrencyCode(getCurrencyCode());
		effortBasedBillingEntity.setStartDate(getStartDate());

		return effortBasedBillingEntity;
	}
}
