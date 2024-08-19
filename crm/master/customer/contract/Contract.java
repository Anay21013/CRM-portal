package com.erp.crm.master.customer.contract;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.erp.core.base.BaseDTO;
import com.erp.crm.master.customer.Customer;
import com.erp.crm.master.customer.contract.effort.ContractRateChart;
import com.erp.crm.master.customer.contract.fixed.FixedBilling;

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
public class Contract extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long customer;
	private Long orgId;
	private String name;
	private LocalDate startDate;
	private Long owner;
	private Integer type;
	
	private Customer customerObject;
	private List<ContractRateChart> effortBasedBillings;
	private List<FixedBilling> fixedBillings;
	

	public Contract(ContractEntity contractEntity) {
		this.id = contractEntity.getId();
		this.orgId = contractEntity.getOrgId();
		this.customer = contractEntity.getCustomer();
		this.name = contractEntity.getName();
		this.startDate = contractEntity.getStartDate();
		this.owner = contractEntity.getOwner();
		this.type = contractEntity.getType();

		super.populateBaseDTO(contractEntity);
	}

	public ContractEntity populateContractEntity() {
		ContractEntity contractEntity = new ContractEntity();
		super.populateBaseEntity(contractEntity);

		contractEntity.setId(getId());
		contractEntity.setOrgId(getOrgId());
		contractEntity.setCustomer(getCustomer());
		contractEntity.setName(getName());
		contractEntity.setStartDate(getStartDate());
		contractEntity.setOwner(getOwner());
		contractEntity.setType(getType());

		return contractEntity;
	}
}
