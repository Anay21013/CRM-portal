/**
 * 
 */
package com.erp.crm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.core.exceptions.BusinessException;
import com.erp.core.security.user.UserDetailsImpl;
import com.erp.crm.master.MasterCustomer;
import com.erp.crm.master.MasterCustomerEntity;
import com.erp.crm.master.MasterCustomerRepository;
import com.erp.crm.master.customer.Customer;
import com.erp.crm.master.customer.CustomerEntity;
import com.erp.crm.master.customer.CustomerRepository;
import com.erp.crm.master.customer.contract.Contract;
import com.erp.crm.master.customer.contract.ContractEntity;
import com.erp.crm.master.customer.contract.ContractRepository;
import com.erp.crm.master.customer.contract.EContractType;
import com.erp.crm.master.customer.contract.allocation.ContractAllocations;
import com.erp.crm.master.customer.contract.allocation.ContractAllocationsEntity;
import com.erp.crm.master.customer.contract.allocation.ContractAllocationsRepository;
import com.erp.crm.master.customer.contract.effort.ContractRateChart;
import com.erp.crm.master.customer.contract.effort.ContractRateChartEntity;
import com.erp.crm.master.customer.contract.effort.ContractRateChartRepository;
import com.erp.crm.master.customer.contract.fixed.FixedBilling;
import com.erp.crm.master.customer.contract.fixed.FixedBillingEntity;
import com.erp.crm.master.customer.contract.fixed.FixedBillingRepository;
import com.erp.crm.master.customer.opportunity.Opportunity;
import com.erp.crm.master.customer.opportunity.OpportunityEntity;
import com.erp.crm.master.customer.opportunity.OpportunityRepository;
import com.erp.crm.master.rate.MasterRateChart;
import com.erp.crm.master.rate.MasterRateChartEntity;
import com.erp.crm.master.rate.MasterRateChartRepository;

import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;


/**
 * 
 */
@Log4j2
@Service
@Transactional
public class CrmService implements ICrmService {

	@Autowired
	private MasterCustomerRepository masterCustomerRepository;

	@Autowired
	private MasterRateChartRepository rateChartRepository;

	@Autowired
	private OpportunityRepository opportunityRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private ContractRateChartRepository effortBasedBillingRepository;

	@Autowired
	private FixedBillingRepository fixedBillingRepository;
	
	@Autowired
	private ContractAllocationsRepository contractAllocationsRepository;

	@Override
	public MasterCustomer addMasterCustomer(MasterCustomer masterCustomer) {

		MasterCustomerEntity masterCustomerEntity = masterCustomer.populateMasterCustomerEntity();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		masterCustomerEntity.setOrgId(userDetail.getOrgId());

		masterCustomerEntity = masterCustomerRepository.save(masterCustomerEntity);

		log.debug("Added Master Customer with id {} & name {}", masterCustomerEntity.getId(),
				masterCustomerEntity.getName());

		return new MasterCustomer(masterCustomerEntity);
	}

	@Override
	public MasterCustomer updateMasterCustomer(MasterCustomer masterCustomer) {
		if (masterCustomer.getId() == null) {
			throw new BusinessException("Id not availbale for master customer");
		}
		MasterCustomerEntity masterCustomerEntityFromDb = masterCustomerRepository
				.getByIdAndDeletedFalse(masterCustomer.getId()).get(0);
		MasterCustomerEntity masterCustomerEntity = masterCustomer.populateMasterCustomerEntity();
		if (!masterCustomerEntity.equals(masterCustomerEntityFromDb)) {
			masterCustomerEntity.setId(masterCustomerEntityFromDb.getId());
			masterCustomerEntity.setOrgId(masterCustomerEntityFromDb.getOrgId());
			masterCustomerEntity.setCreatedBy(masterCustomerEntityFromDb.getCreatedBy());
			masterCustomerEntity.setCreatedDatetime(masterCustomerEntityFromDb.getCreatedDatetime());
			masterCustomerEntity.setLastModifiedBy(null);
			masterCustomerEntity.setLastModifiedDatetime(null);

			masterCustomerEntity = masterCustomerRepository.save(masterCustomerEntity);

			log.debug("Updated Master Customer with id {} & name {}", masterCustomerEntity.getId(),
					masterCustomerEntity.getName());
			return new MasterCustomer(masterCustomerEntity);

		} else {
			return masterCustomer;
		}
	}

	@Override
	public MasterCustomer getMasterCustomer(Long masterCustomerId) {
		MasterCustomerEntity masterCustomerEntity = masterCustomerRepository.getByIdAndDeletedFalse(masterCustomerId)
				.get(0);
		MasterCustomer masterCustomer = new MasterCustomer(masterCustomerEntity);


		List<MasterRateChartEntity> rateCharts = rateChartRepository.getByOrgIdAndMasterCustomerAndDeletedFalse(
				masterCustomerEntity.getOrgId(), masterCustomerEntity.getId());
		if (rateCharts != null && !rateCharts.isEmpty()) {
			MasterRateChart rateChart = new MasterRateChart();
			masterCustomer.setRateChart(rateChart);
		}

		List<Customer> customers = new ArrayList<>();
		customerRepository.getByOrgIdAndMasterCustomerAndDeletedFalse(masterCustomerEntity.getOrgId(),
				masterCustomerEntity.getId()).forEach(ce -> customers.add(new Customer(ce)));
		masterCustomer.setCustomers(customers);

		return masterCustomer;
	}

	@Override
	public List<MasterCustomer> getMasterCustomers() {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		List<MasterCustomer> masterCustomers = new ArrayList<>();
			masterCustomerRepository.getByOrgIdAndDeletedFalse(userDetail.getOrgId())
					.forEach(masterCustomerEntity -> masterCustomers.add(new MasterCustomer(masterCustomerEntity)));

		return masterCustomers;
	}

	@Override
	public List<MasterRateChart> addRateChart(List<MasterRateChart> rateCharts, Long masterCustomerId) {
		if (masterCustomerId == null) {
			throw new BusinessException("Id not availbale for master customer");
		}
		List<MasterRateChartEntity> rateChartEntities = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		rateCharts.forEach(rc -> {
			rc.setOrgId(userDetail.getOrgId());
			rc.setMasterCustomer(masterCustomerId);
			rateChartEntities.add(rc.populateRateChartEntity());
		});

		rateChartRepository.saveAll(rateChartEntities);

		return getActiveRateChartsForMaster(masterCustomerId);
	}

	@Override
	public MasterRateChart updateRateChart(MasterRateChart rateChart) {
		if (rateChart.getId() == null) {
			throw new BusinessException("Id not availbale for rateChart");
		}
		MasterRateChartEntity rateChartEntityFromDb = rateChartRepository.getByIdAndDeletedFalse(rateChart.getId()).get(0);
		MasterRateChartEntity rateChartEntity = rateChart.populateRateChartEntity();
		if (!rateChartEntity.equals(rateChartEntityFromDb)) {
			rateChartEntity.setId(rateChartEntityFromDb.getId());
			rateChartEntity.setOrgId(rateChartEntityFromDb.getOrgId());
			rateChartEntity.setCreatedBy(rateChartEntityFromDb.getCreatedBy());
			rateChartEntity.setCreatedDatetime(rateChartEntityFromDb.getCreatedDatetime());
			rateChartEntity.setLastModifiedBy(null);
			rateChartEntity.setLastModifiedDatetime(null);

			rateChartEntity = rateChartRepository.save(rateChartEntity);

			log.debug("Updated rateChart with id {} & master customer {}", rateChartEntity.getId(),
					rateChartEntity.getMasterCustomer());
			return new MasterRateChart(rateChartEntity);

		} else {
			return rateChart;
		}
	}

	@Override
	public List<MasterRateChart> getActiveRateChartsForMaster(Long masterCustomerId) {
		List<MasterRateChart> rateCharts = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		rateChartRepository.getByOrgIdAndMasterCustomerAndDeletedFalse(userDetail.getOrgId(), masterCustomerId).forEach(rce -> {
					rateCharts.add(new MasterRateChart(rce));
				});
		log.debug("{} rate charts for given master id {}", rateCharts.size(), masterCustomerId);
		return rateCharts;
	}

	@Override
	public Customer addCustomer(Customer customer, Long masterCustomerId) {

		CustomerEntity customerEntity = customer.populateCustomerEntity();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		customerEntity.setOrgId(userDetail.getOrgId());
		customerEntity.setMasterCustomer(masterCustomerId);
		customerEntity = customerRepository.save(customerEntity);

		log.debug("Added Master Customer with id {} & name {}", customerEntity.getId(), customerEntity.getName());

		return new Customer(customerEntity);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		if (customer.getId() == null) {
			throw new BusinessException("Id not availbale for customer");
		}
		CustomerEntity customerEntityFromDb = customerRepository.getByIdAndDeletedFalse(customer.getId()).get(0);
		CustomerEntity customerEntity = customer.populateCustomerEntity();
		if (!customerEntity.equals(customerEntityFromDb)) {
			customerEntity.setId(customerEntityFromDb.getId());
			customerEntity.setOrgId(customerEntityFromDb.getOrgId());
			customerEntity.setCreatedBy(customerEntityFromDb.getCreatedBy());
			customerEntity.setCreatedDatetime(customerEntityFromDb.getCreatedDatetime());
			customerEntity.setLastModifiedBy(null);
			customerEntity.setLastModifiedDatetime(null);

			customerEntity = customerRepository.save(customerEntity);

			log.debug("Updated Customer with id {} & name {}", customerEntity.getId(), customerEntity.getName());
			return new Customer(customerEntity);

		} else {
			return customer;
		}
	}

	@Override
	public Customer getCustomer(Long customerId) {
		CustomerEntity customerEntity = customerRepository.getByIdAndDeletedFalse(customerId).get(0);
		Customer customer = new Customer(customerEntity);
		customer.setContracts(getActiveContractsByCustomerId(customerId));
		return customer;
	}

	@Override
	public List<Customer> getCustomersByMasterId(Long masterCustomerId) {
		List<Customer> customers = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		customerRepository.getByOrgIdAndMasterCustomerAndDeletedFalse(userDetail.getOrgId(), masterCustomerId)
				.forEach(ce -> {
					customers.add(new Customer(ce));
				});
		log.debug("{} Customer for given master id {}", customers.size(), masterCustomerId);
		return customers;
	}

	@Override
	public Opportunity addOpportunity(Opportunity opportunity, Long customerId) {

		OpportunityEntity opportunityEntity = opportunity.populateOpportunityEntity();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		opportunityEntity.setOrgId(userDetail.getOrgId());
		opportunityEntity.setCustomer(customerId);
		opportunityEntity = opportunityRepository.save(opportunityEntity);

		log.debug("Added Opportunity with id {} & name {}", opportunityEntity.getId(), opportunityEntity.getName());
		return new Opportunity(opportunityEntity);
	}

	@Override
	public Opportunity updateOpportunity(Opportunity opportunity) {
		if (opportunity.getId() == null) {
			throw new BusinessException("Id not availbale for contract");
		}
		OpportunityEntity opportunityEntityFromDb = opportunityRepository.getByIdAndDeletedFalse(opportunity.getId())
				.get(0);
		OpportunityEntity opportunityEntity = opportunity.populateOpportunityEntity();
		if (!opportunityEntity.equals(opportunityEntityFromDb)) {
			opportunityEntity.setId(opportunityEntityFromDb.getId());
			opportunityEntity.setOrgId(opportunityEntityFromDb.getOrgId());
			opportunityEntity.setCreatedBy(opportunityEntityFromDb.getCreatedBy());
			opportunityEntity.setCreatedDatetime(opportunityEntityFromDb.getCreatedDatetime());
			opportunityEntity.setLastModifiedBy(null);
			opportunityEntity.setLastModifiedDatetime(null);

			opportunityEntity = opportunityRepository.save(opportunityEntity);

			log.debug("Updated Opportunity with id {} & name {}", opportunityEntity.getId(),
					opportunityEntity.getName());
			return new Opportunity(opportunityEntity);

		} else {
			return opportunity;
		}
	}

	@Override
	public Opportunity getOpportunity(Long opportunityId) {
		OpportunityEntity contractEntity = opportunityRepository.getByIdAndDeletedFalse(opportunityId).get(0);
		Opportunity opportunity = new Opportunity(contractEntity);


		customerRepository.getByIdAndDeletedFalse(opportunity.getCustomer())
				.forEach(ce -> opportunity.setCustomerObject(new Customer(ce)));

		return opportunity;
	}

	@Override
	public List<Opportunity> getAllOpportunityByCustomerId(Long customerId) {
		List<Opportunity> opportunities = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		opportunityRepository.getByOrgIdAndCustomerAndDeletedFalse(userDetail.getOrgId(), customerId).forEach(ce -> {
			opportunities.add(new Opportunity(ce));
		});
		log.debug("{} Opportunity for given customer id {}", opportunities.size(), customerId);
		return opportunities;
	}

	@Override
	public Contract addContract(Contract contract, Long customerId) {

		ContractEntity contractEntity = contract.populateContractEntity();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		contractEntity.setOrgId(userDetail.getOrgId());
		contractEntity.setCustomer(customerId);
		contractEntity = contractRepository.save(contractEntity);

		log.debug("Added Master Opportunity with id {} & name {}", contractEntity.getId(), contractEntity.getName());
		return new Contract(contractEntity);
	}

	@Override
	public Contract updateContract(Contract contract) {
		if (contract.getId() == null) {
			throw new BusinessException("Id not availbale for contract");
		}
		ContractEntity contractEntityFromDb = contractRepository.getByIdAndDeletedFalse(contract.getId()).get(0);
		ContractEntity contractEntity = contract.populateContractEntity();
		if (!contractEntity.equals(contractEntityFromDb)) {
			contractEntity.setId(contractEntityFromDb.getId());
			contractEntity.setOrgId(contractEntityFromDb.getOrgId());
			contractEntity.setCreatedBy(contractEntityFromDb.getCreatedBy());
			contractEntity.setCreatedDatetime(contractEntityFromDb.getCreatedDatetime());
			contractEntity.setLastModifiedBy(null);
			contractEntity.setLastModifiedDatetime(null);

			contractEntity = contractRepository.save(contractEntity);

			log.debug("Updated Opportunity with id {} & name {}", contractEntity.getId(), contractEntity.getName());
			return new Contract(contractEntity);

		} else {
			return contract;
		}
	}

	@Override
	public Contract getContract(Long contractId) {
		ContractEntity contractEntity = contractRepository.getByIdAndDeletedFalse(contractId).get(0);
		Contract contract = new Contract(contractEntity);
		if (contract.getType().equals(EContractType.EFFORT_BASED.getValue())) {
//			List<ContractRateChart> effortBasedBillings = new ArrayList<>();
//			effortBasedBillingRepository.getByOrgIdAndContractAndDeletedFalse(contract.getOrgId(), contract.getId())
//					.forEach(ebbe -> effortBasedBillings.add(new ContractRateChart(ebbe)));

			contract.setEffortBasedBillings(getActiveEffortBasedBillingForContract(contractId));
		} else if (contract.getType().equals(EContractType.FIXED_PRICE.getValue())) {
//			List<FixedBilling> fixedBillings = new ArrayList<>();
//			fixedBillingRepository.getByOrgIdAndContractAndDeletedFalse(contract.getOrgId(), contract.getId())
//					.forEach(fbe -> fixedBillings.add(new FixedBilling(fbe)));

			contract.setFixedBillings(getActiveFixedBillingForContract(contractId));
		}

		customerRepository.getByIdAndDeletedFalse(contract.getCustomer())
				.forEach(ce -> contract.setCustomerObject(new Customer(ce)));

		return contract;
	}

	@Override
	public List<Contract> getAllContractsByCustomerId(Long customerId) {
		List<Contract> contracts = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		contractRepository.getByOrgIdAndCustomerAndDeletedFalse(userDetail.getOrgId(), customerId).forEach(ce -> {
			contracts.add(new Contract(ce));
		});
		log.debug("{} Opportunity for given master id {}", contracts.size(), customerId);
		return contracts;
	}

	@Override
	public List<Contract> getActiveContractsByCustomerId(Long customerId) {
		List<Contract> contracts = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		contractRepository.getByOrgIdAndCustomerAndDeletedFalse(userDetail.getOrgId(), customerId).forEach(ce -> {
			contracts.add(new Contract(ce));
		});
		log.debug("{} Active Contracts for given master id {}", contracts.size(), customerId);
		return contracts;
	}

	@Override
	public List<ContractRateChart> addEffortBasedBillingForContract(List<ContractRateChart> effortBasedBillings,
			Long contract) {
		if (contract == null) {
			throw new BusinessException("Id not availbale for contract");
		}
		List<ContractRateChartEntity> effortBasedBillingEntities = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		effortBasedBillings.forEach(ebb -> {
			ebb.setOrgId(userDetail.getOrgId());
			ebb.setContract(contract);
			effortBasedBillingEntities.add(ebb.populateEffortBasedBillingEntity());
		});

		effortBasedBillingRepository.saveAll(effortBasedBillingEntities);

		return getActiveEffortBasedBillingForContract(contract);
	}

	@Override
	public ContractRateChart updateEffortBasedBillingForContract(ContractRateChart effortBasedBilling) {
		if (effortBasedBilling.getId() == null) {
			throw new BusinessException("Id not availbale for effortBasedBilling");
		}
		ContractRateChartEntity effortBasedBillingEntityFromDb = effortBasedBillingRepository
				.getByIdAndDeletedFalse(effortBasedBilling.getId()).get(0);
		ContractRateChartEntity effortBasedBillingEntity = effortBasedBilling.populateEffortBasedBillingEntity();
		if (!effortBasedBillingEntity.equals(effortBasedBillingEntityFromDb)) {
			effortBasedBillingEntity.setId(effortBasedBillingEntityFromDb.getId());
			effortBasedBillingEntity.setOrgId(effortBasedBillingEntityFromDb.getOrgId());
			effortBasedBillingEntity.setCreatedBy(effortBasedBillingEntityFromDb.getCreatedBy());
			effortBasedBillingEntity.setCreatedDatetime(effortBasedBillingEntityFromDb.getCreatedDatetime());
			effortBasedBillingEntity.setLastModifiedBy(null);
			effortBasedBillingEntity.setLastModifiedDatetime(null);

			effortBasedBillingEntity = effortBasedBillingRepository.save(effortBasedBillingEntity);

			log.debug("Updated effortBasedBilling with id {} & master customer {}", effortBasedBillingEntity.getId(),
					effortBasedBillingEntity.getContract());
			return new ContractRateChart(effortBasedBillingEntity);

		} else {
			return effortBasedBilling;
		}
	}

	@Override
	public List<ContractRateChart> getActiveEffortBasedBillingForContract(Long contractId) {
		List<ContractRateChart> effortBasedBilling = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		effortBasedBillingRepository.getByOrgIdAndContractAndDeletedFalse(userDetail.getOrgId(), contractId).forEach(ebb -> {
					effortBasedBilling.add(new ContractRateChart(ebb));
				});
		log.debug("{} Active ContractRateChart for given contract id {}", effortBasedBilling.size(), contractId);
		return effortBasedBilling;
	}

	@Override
	public List<FixedBilling> addFixedBillingForContract(List<FixedBilling> fixedBillings, Long contract) {
		if (contract == null) {
			throw new BusinessException("Id not availbale for contract");
		}
		List<FixedBillingEntity> fixedBillingEntities = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		fixedBillings.forEach(fb -> {
			fb.setOrgId(userDetail.getOrgId());
			fb.setContract(contract);
			fixedBillingEntities.add(fb.populateFixedBillingEntity());
		});

		fixedBillingRepository.saveAll(fixedBillingEntities);

		return getActiveFixedBillingForContract(contract);
	}

	@Override
	public FixedBilling updateFixedBillingForContract(FixedBilling fixedBilling) {
		if (fixedBilling.getId() == null) {
			throw new BusinessException("Id not availbale for fixedBilling");
		}
		FixedBillingEntity fixedBillingEntityFromDb = fixedBillingRepository
				.getByIdAndDeletedFalse(fixedBilling.getId()).get(0);
		FixedBillingEntity fixedBillingEntity = fixedBilling.populateFixedBillingEntity();
		if (!fixedBillingEntity.equals(fixedBillingEntityFromDb)) {
			fixedBillingEntity.setId(fixedBillingEntityFromDb.getId());
			fixedBillingEntity.setOrgId(fixedBillingEntityFromDb.getOrgId());
			fixedBillingEntity.setCreatedBy(fixedBillingEntityFromDb.getCreatedBy());
			fixedBillingEntity.setCreatedDatetime(fixedBillingEntityFromDb.getCreatedDatetime());
			fixedBillingEntity.setLastModifiedBy(null);
			fixedBillingEntity.setLastModifiedDatetime(null);

			fixedBillingEntity = fixedBillingRepository.save(fixedBillingEntity);

			log.debug("Updated fixedBilling with id {} & master customer {}", fixedBillingEntity.getId(),
					fixedBillingEntity.getContract());
			return new FixedBilling(fixedBillingEntity);

		} else {
			return fixedBilling;
		}
	}

	@Override
	public List<FixedBilling> getActiveFixedBillingForContract(Long contractId) {
		List<FixedBilling> fixedBilling = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		fixedBillingRepository.getByOrgIdAndContractAndDeletedFalse(userDetail.getOrgId(), contractId).forEach(ebb -> {
			fixedBilling.add(new FixedBilling(ebb));
		});
		log.debug("{} Active FixedBilling for given contract id {}", fixedBilling.size(), contractId);
		return fixedBilling;
	}

	@Override
	public ContractAllocations addContractAllocation(ContractAllocations contractAllocation) {

		ContractAllocationsEntity contractAllocationsEntity = contractAllocation.populateContractAllocationsEntity();
		if (contractAllocation.getAllocationPercentage() == null) {
			throw new BusinessException("Allocation Percentage is mandatory");
		} else {
			BigDecimal totalAllocationPercentage = contractAllocation.getAllocationPercentage();
			List<ContractAllocations> contractAllocationList = getAllContractAllocationsForEmployee(
					contractAllocation.getEmployeeId(), contractAllocation.getAllocationStartDate());
			if (!contractAllocationList.isEmpty()) {
				for (ContractAllocations ca : contractAllocationList) {
					totalAllocationPercentage = totalAllocationPercentage.add(ca.getAllocationPercentage());
				}
			}
			if (totalAllocationPercentage.compareTo(new BigDecimal(100)) > 0) {
				throw new BusinessException(
						"Total allocation for Employee id " + contractAllocation.getEmployeeId() + " exceeds 100%");
			}
		}
		
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		contractAllocationsEntity.setOrgId(userDetail.getOrgId());
		contractAllocationsEntity = contractAllocationsRepository.save(contractAllocationsEntity);

		log.debug("Added contractAllocation with id {}", contractAllocationsEntity.getId());
		return new ContractAllocations(contractAllocationsEntity);
	}
	
	@Override
	public List<ContractAllocations> addContractAllocations(List<ContractAllocations> contractAllocations) {
		List<ContractAllocations> contractAllocationList = new ArrayList<>();
		List<ContractAllocationsEntity> contractAllocationEntityList = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		contractAllocations.forEach(contractAllocation -> {
			if (contractAllocation.getAllocationPercentage() == null) {
				throw new BusinessException("Allocation Percentage is mandatory");
			} else {
				BigDecimal totalAllocationPercentage = contractAllocation.getAllocationPercentage();
				List<ContractAllocations> empContractAllocationList = getAllContractAllocationsForEmployee(contractAllocation.getEmployeeId(),
						contractAllocation.getAllocationStartDate());
				if(!empContractAllocationList.isEmpty()) {
					for(ContractAllocations ca : empContractAllocationList) {
						totalAllocationPercentage = totalAllocationPercentage.add(ca.getAllocationPercentage());
					}
				}
				if(totalAllocationPercentage.compareTo(new BigDecimal(100)) > 0) {
					throw new BusinessException("Total allocation for Employee id "+contractAllocation.getEmployeeId()+" exceeds 100%");
				}
			}
			
			ContractAllocationsEntity contractAllocationsEntity = contractAllocation
					.populateContractAllocationsEntity();
			contractAllocationsEntity.setOrgId(userDetail.getOrgId());
			contractAllocationEntityList.add(contractAllocationsEntity);
		});

		contractAllocationsRepository.saveAll(contractAllocationEntityList)
				.forEach(cae -> contractAllocationList.add(new ContractAllocations(cae)));

		log.debug("Added {} contractAllocations", contractAllocationList.size());
		return contractAllocationList;
	}

	@Override
	public ContractAllocations updateContractAllocations(ContractAllocations contractAllocation) {
		if (contractAllocation.getId() == null) {
			throw new BusinessException("Id not availbale for contract");
		}
		if (contractAllocation.getAllocationPercentage() == null) {
			throw new BusinessException("Allocation Percentage is mandatory");
		} else {
			BigDecimal totalAllocationPercentage = contractAllocation.getAllocationPercentage();
			List<ContractAllocations> contractAllocationList = getAllContractAllocationsForEmployee(
					contractAllocation.getEmployeeId(), contractAllocation.getAllocationStartDate());
			if (!contractAllocationList.isEmpty()) {
				for (ContractAllocations ca : contractAllocationList) {
					totalAllocationPercentage = totalAllocationPercentage.add(ca.getAllocationPercentage());
				}
			}
			if (totalAllocationPercentage.compareTo(new BigDecimal(100)) > 0) {
				throw new BusinessException(
						"Total allocation for Employee id " + contractAllocation.getEmployeeId() + " exceeds 100%");
			}
		}
		
		ContractAllocationsEntity contractAllocationFromDb = contractAllocationsRepository.getByIdAndDeletedFalse(contractAllocation.getId()).get(0);
		ContractAllocationsEntity contractAllocationEntity = contractAllocation.populateContractAllocationsEntity();
		if (!contractAllocationEntity.equals(contractAllocationFromDb)) {
			contractAllocationEntity.setId(contractAllocationFromDb.getId());
			contractAllocationEntity.setOrgId(contractAllocationFromDb.getOrgId());
			contractAllocationEntity.setCreatedBy(contractAllocationFromDb.getCreatedBy());
			contractAllocationEntity.setCreatedDatetime(contractAllocationFromDb.getCreatedDatetime());
			contractAllocationEntity.setLastModifiedBy(null);
			contractAllocationEntity.setLastModifiedDatetime(null);

			contractAllocationEntity = contractAllocationsRepository.save(contractAllocationEntity);

			log.debug("Updated contractAllocation with id {}", contractAllocationEntity.getId());
			return new ContractAllocations(contractAllocationEntity);

		} else {
			return contractAllocation;
		}
	}

	@Override
	public ContractAllocations getContractAllocations(Long contractAllocationId) {
		ContractAllocationsEntity contractAllocationEntity = contractAllocationsRepository
				.getByIdAndDeletedFalse(contractAllocationId).get(0);
		return new ContractAllocations(contractAllocationEntity);
	}

	@Override
	public List<ContractAllocations> getAllContractAllocations(Long contractID) {
		List<ContractAllocations> contractAllocationList = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		contractAllocationsRepository.getByOrgIdAndContractAndDeletedFalse(userDetail.getOrgId(), contractID)
				.forEach(cae -> contractAllocationList.add(new ContractAllocations(cae)));
		return contractAllocationList;
	}

	@Override
	public List<ContractAllocations> getAllContractAllocationsForEmployee(Long employeeID,
			LocalDate allocationStartDate) {
		List<ContractAllocations> contractAllocationList = new ArrayList<>();
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new JwtException("Authorization can not be empty.");
		}
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		contractAllocationsRepository
				.getByOrgAndEmployeeAndAllocationStartDate(userDetail.getOrgId(), employeeID, allocationStartDate)
				.forEach(cae -> contractAllocationList.add(new ContractAllocations(cae)));
		return contractAllocationList;
	}

}
