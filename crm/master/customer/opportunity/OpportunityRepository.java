package com.erp.crm.master.customer.opportunity;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OpportunityRepository extends CrudRepository<OpportunityEntity, Long> {

	List<OpportunityEntity> getByIdAndDeletedFalse(Long id);

	List<OpportunityEntity> getByOrgIdAndDeletedFalse(Long orgId);

	List<OpportunityEntity> getByOrgIdAndCustomerAndDeletedFalse(Long orgId, Long customer);

	List<OpportunityEntity> getByOrgIdAndOwnerAndDeletedFalse(Long orgId, Long owner);

	List<OpportunityEntity> getByOrgIdAndTypeAndDeletedFalse(Long orgId, Integer type);

	List<OpportunityEntity> getByOrgIdAndTypeAndOwnerAndDeletedFalse(Long orgId, Integer type, Long owner);

	List<OpportunityEntity> getByOrgIdAndCustomerAndOwnerAndDeletedFalse(Long orgId, Long customer, Long owner);

	List<OpportunityEntity> getByOrgIdAndCustomerAndTypeAndDeletedFalse(Long orgId, Long customer, Integer type);

	List<OpportunityEntity> getByOrgIdAndCustomerAndOwnerAndTypeAndDeletedFalse(Long orgId, Long customer, Long owner,
			Integer type);

	List<OpportunityEntity> getByOwnerAndDeletedFalse(Long owner);
}
