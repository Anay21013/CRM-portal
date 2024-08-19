package com.erp.crm.master;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface MasterCustomerRepository extends CrudRepository<MasterCustomerEntity, Long> {

	List<MasterCustomerEntity> getByIdAndDeletedFalse(Long id);
	
	List<MasterCustomerEntity> getByDeletedFalse();
	
	List<MasterCustomerEntity> getByOrgIdAndDeletedFalse(Long orgId);
}
