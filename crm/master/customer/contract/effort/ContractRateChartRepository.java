package com.erp.crm.master.customer.contract.effort;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ContractRateChartRepository extends CrudRepository<ContractRateChartEntity, Long> {

	List<ContractRateChartEntity> getByIdAndDeletedFalse(Long id);
	
	List<ContractRateChartEntity> getByOrgIdAndContractAndDeletedFalse(Long orgId, Long contract);
	
}
