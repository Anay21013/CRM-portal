package com.erp.crm.master.rate;

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
public class MasterRateChart extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long masterCustomer;
	private Long orgId;
	private String roleName;
	private BigDecimal roleRate;
	private Integer roleRateFrequency;
	private String currencyCode;
	private LocalDate startDate;
	
	public MasterRateChart(MasterRateChartEntity rateChartEntity) {
		this.id = rateChartEntity.getId();
		this.orgId = rateChartEntity.getOrgId();
		this.masterCustomer = rateChartEntity.getMasterCustomer();
		this.roleName = rateChartEntity.getRoleName();
		this.roleRate = rateChartEntity.getRoleRate();
		this.roleRateFrequency = rateChartEntity.getRoleRateFrequency();
		this.currencyCode = rateChartEntity.getCurrencyCode();
		this.startDate = rateChartEntity.getStartDate();

		super.populateBaseDTO(rateChartEntity);
	}

	public MasterRateChartEntity populateRateChartEntity() {
		MasterRateChartEntity rateChartEntity = new MasterRateChartEntity();
		super.populateBaseEntity(rateChartEntity);

		rateChartEntity.setId(getId());
		rateChartEntity.setOrgId(getOrgId());
		rateChartEntity.setMasterCustomer(getMasterCustomer());
		rateChartEntity.setRoleName(getRoleName());
		rateChartEntity.setRoleRate(getRoleRate());
		rateChartEntity.setRoleRateFrequency(getRoleRateFrequency());
		rateChartEntity.setCurrencyCode(getCurrencyCode());
		rateChartEntity.setStartDate(getStartDate());

		return rateChartEntity;
	}
}
