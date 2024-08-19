package com.erp.crm.master.customer.contract.fixed;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface FixedBillingRepository extends CrudRepository<FixedBillingEntity, Long> {

	List<FixedBillingEntity> getByIdAndDeletedFalse(Long id);
	
	List<FixedBillingEntity> getByOrgIdAndContractAndDeletedFalse(Long orgId, Long contract);
}
