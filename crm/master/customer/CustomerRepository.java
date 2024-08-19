package com.erp.crm.master.customer;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

	List<CustomerEntity> getByIdAndDeletedFalse(Long id);
	
	List<CustomerEntity> getByOrgIdAndMasterCustomerAndDeletedFalse(Long orgId, Long masterCustomer);
}
