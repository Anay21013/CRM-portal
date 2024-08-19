package com.erp.crm.master.rate;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface MasterRateChartRepository extends CrudRepository<MasterRateChartEntity, Long> {
	
	List<MasterRateChartEntity> getByIdAndDeletedFalse(Long id);

	List<MasterRateChartEntity> getByOrgIdAndMasterCustomerAndDeletedFalse(Long orgId, Long masterCustomer);
}
