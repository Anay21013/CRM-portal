package com.erp.crm.master.customer.contract.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.erp.core.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "crm_contract_allocations")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
public class ContractAllocationsEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "org_id")
	private Long orgId;
	
	@NotNull
	@Column(name = "contract")
	private Long contract;
	
	@NotNull
	@Column(name = "employee_id")
	private Long employeeId;

	@Column(name = "allocation_start_date")
	private LocalDate allocationStartDate;
	
	@Column(name = "allocation_end_date")
	private LocalDate allocationEndDate;

	@Column(name = "allocation_percentage")
	private BigDecimal allocationPercentage;
	
	@Column(name = "master_rate_chart_id")
	private Long masterRateChartId;
	
	@Column(name = "contract_rate_chart_id")
	private Long contractRateChartId;
	
}
