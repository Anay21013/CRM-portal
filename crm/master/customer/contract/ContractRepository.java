package com.erp.crm.master.customer.contract;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ContractRepository extends CrudRepository<ContractEntity, Long> {

	List<ContractEntity> getByIdAndDeletedFalse(Long id);
	
	List<ContractEntity> getByOrgIdAndDeletedFalse(Long orgId);
	
	List<ContractEntity> getByOrgIdAndCustomerAndDeletedFalse(Long orgId, Long customer);
	
	List<ContractEntity> getByOrgIdAndOwnerAndDeletedFalse(Long orgId, Long owner);
	
	List<ContractEntity> getByOrgIdAndTypeAndDeletedFalse(Long orgId, Integer type);
	
	List<ContractEntity> getByOrgIdAndTypeAndOwnerAndDeletedFalse(Long orgId, Integer type, Long owner);
	
	List<ContractEntity> getByOrgIdAndCustomerAndOwnerAndDeletedFalse(Long orgId, Long customer, Long owner);
	
	List<ContractEntity> getByOrgIdAndCustomerAndTypeAndDeletedFalse(Long orgId, Long customer, Integer type);
	
	List<ContractEntity> getByOrgIdAndCustomerAndOwnerAndTypeAndDeletedFalse(Long orgId, Long customer, Long owner, Integer type);
	
//	List<ContractEntity> getByOrgIdAndCustomerAndOwnerAndTypeAndStatusAndDeletedFalse(Long orgId, Long customer, Long owner, Integer type, Integer status);
	
//	List<ContractEntity> getByOrgIdAndCustomerAndStatusAndDeletedFalse(Long orgId, Long customer, Integer status);
	
	List<ContractEntity> getByOwnerAndDeletedFalse(Long owner);
}
