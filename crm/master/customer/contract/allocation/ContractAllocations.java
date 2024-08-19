package com.erp.crm.master.customer.contract.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.erp.core.base.BaseDTO;
import com.erp.crm.master.customer.contract.Contract;

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
public class ContractAllocations extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long orgId;
	private Long contract;
	private Long employeeId;
	private LocalDate allocationStartDate;
	private LocalDate allocationEndDate;
	private BigDecimal allocationPercentage;
	private Long masterRateChartId;
	private Long contractRateChartId;

	private Contract contractObject;

	public ContractAllocations(ContractAllocationsEntity contractAllocationsEntity) {
		this.id = contractAllocationsEntity.getId();
		this.orgId = contractAllocationsEntity.getOrgId();
		this.contract = contractAllocationsEntity.getContract();
		this.employeeId = contractAllocationsEntity.getEmployeeId();
		this.allocationStartDate = contractAllocationsEntity.getAllocationStartDate();
		this.allocationEndDate = contractAllocationsEntity.getAllocationEndDate();
		this.allocationPercentage = contractAllocationsEntity.getAllocationPercentage();
		this.masterRateChartId = contractAllocationsEntity.getMasterRateChartId();
		this.contractRateChartId = contractAllocationsEntity.getContractRateChartId();

		super.populateBaseDTO(contractAllocationsEntity);
	}

	public ContractAllocationsEntity populateContractAllocationsEntity() {
		ContractAllocationsEntity contractAllocationsEntity = new ContractAllocationsEntity();
		super.populateBaseEntity(contractAllocationsEntity);

		contractAllocationsEntity.setId(getId());
		contractAllocationsEntity.setOrgId(getOrgId());
		contractAllocationsEntity.setContract(getContract());
		contractAllocationsEntity.setEmployeeId(getEmployeeId());
		contractAllocationsEntity.setAllocationStartDate(getAllocationStartDate());
		contractAllocationsEntity.setAllocationEndDate(getAllocationEndDate());
		contractAllocationsEntity.setAllocationPercentage(getAllocationPercentage());
		contractAllocationsEntity.setMasterRateChartId(getMasterRateChartId());
		contractAllocationsEntity.setContractRateChartId(getContractRateChartId());

		return contractAllocationsEntity;
	}
}
