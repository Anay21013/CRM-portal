package com.erp.crm.master.customer.contract.allocation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContractAllocationsRepository extends CrudRepository<ContractAllocationsEntity, Long> {

	List<ContractAllocationsEntity> getByOrgIdAndContractAndDeletedFalse(Long orgId, Long contract);

	List<ContractAllocationsEntity> getByOrgIdAndEmployeeId(Long orgId, Long employeeId);
	
	List<ContractAllocationsEntity> getByIdAndDeletedFalse(Long id);

	@Query("SELECT ca FROM ContractAllocationsEntity ca " + "WHERE ca.orgId = :orgId AND ca.employeeId = :employeeId "
			+ "AND ca.allocationStartDate <= :allocationStartDate " + "AND ca.allocationEndDate >= :allocationStartDate "
			+ "AND (ca.deleted = null OR ca.deleted = false ) ORDER BY ca.allocationStartDate DESC")
	List<ContractAllocationsEntity> getByOrgAndEmployeeAndAllocationStartDate(@Param("orgId") Long orgId,
			@Param("employeeId") Long employeeId, @Param("allocationStartDate") LocalDate allocationStartDate);
}
