package com.erp.crm;

import java.time.LocalDate;
import java.util.List;

import com.erp.crm.master.MasterCustomer;
import com.erp.crm.master.customer.Customer;
import com.erp.crm.master.customer.contract.Contract;
import com.erp.crm.master.customer.contract.allocation.ContractAllocations;
import com.erp.crm.master.customer.contract.effort.ContractRateChart;
import com.erp.crm.master.customer.contract.fixed.FixedBilling;
import com.erp.crm.master.customer.opportunity.Opportunity;
import com.erp.crm.master.rate.MasterRateChart;

public interface ICrmService {

	MasterCustomer addMasterCustomer(MasterCustomer masterCustomer);
	MasterCustomer updateMasterCustomer(MasterCustomer masterCustomer);
	MasterCustomer getMasterCustomer(Long masterCustomerId);
	List<MasterCustomer> getMasterCustomers();

	List<MasterRateChart> addRateChart(List<MasterRateChart> rateCharts, Long masterCustomerId);
	MasterRateChart updateRateChart(MasterRateChart rateChart);
	List<MasterRateChart> getActiveRateChartsForMaster(Long masterCustomerId);

	Customer addCustomer(Customer customer, Long masterCustomerId);
	Customer updateCustomer(Customer customer);
	Customer getCustomer(Long customerId);

	List<Customer> getCustomersByMasterId(Long masterCustomerId);

	Opportunity addOpportunity(Opportunity opportunity, Long customerId);
	Opportunity updateOpportunity(Opportunity opportunity);
	Opportunity getOpportunity(Long opportunityId);
	
	List<Opportunity> getAllOpportunityByCustomerId(Long customerId);
	
	Contract addContract(Contract contract, Long customerId);
	Contract updateContract(Contract contract);
	Contract getContract(Long contractId);

	List<Contract> getAllContractsByCustomerId(Long customerId);
	List<Contract> getActiveContractsByCustomerId(Long customerId);

	List<ContractRateChart> addEffortBasedBillingForContract(List<ContractRateChart> effortBasedBillings, Long contract);
	ContractRateChart updateEffortBasedBillingForContract(ContractRateChart effortBasedBilling);
	List<ContractRateChart> getActiveEffortBasedBillingForContract(Long contractId);

	List<FixedBilling> addFixedBillingForContract(List<FixedBilling> fixedBillings, Long contract);
	FixedBilling updateFixedBillingForContract(FixedBilling fixedBilling);
	List<FixedBilling> getActiveFixedBillingForContract(Long contractId);
	
	ContractAllocations addContractAllocation(ContractAllocations contractAllocation);
	List<ContractAllocations> addContractAllocations(List<ContractAllocations> contractAllocations);
	ContractAllocations updateContractAllocations(ContractAllocations contractAllocation);
	ContractAllocations getContractAllocations(Long contractAllocationId);
	
	List<ContractAllocations> getAllContractAllocations(Long contractID);
	List<ContractAllocations> getAllContractAllocationsForEmployee(Long employeeID, LocalDate allocationStartDate);
}
