package com.erp.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.erp.crm.master.MasterCustomer;
import com.erp.crm.master.customer.Customer;
import com.erp.crm.master.customer.contract.Contract;
import com.erp.crm.master.customer.contract.effort.ContractRateChart;
import com.erp.crm.master.customer.contract.fixed.FixedBilling;
import com.erp.crm.master.customer.opportunity.Opportunity;
import com.erp.crm.master.rate.MasterRateChart;

import lombok.extern.log4j.Log4j2;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/crm")
@Log4j2
public class CrmController {

	@Autowired
	private ICrmService crmService;
	
	@GetMapping("")
	@PreAuthorize("@securityService.hasPermission('getMasterCustomer')")
	public ResponseEntity<List<MasterCustomer>> getMasterCustomers() {
		log.debug("Fetching All MasterCustomers");
		try {
			List<MasterCustomer> masterCustomer = crmService.getMasterCustomers();
			log.debug("Fetched {} MasterCustomers", masterCustomer.size());
			return new ResponseEntity<List<MasterCustomer>>(masterCustomer, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching MasterCustomers ", e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching MasterCustomers" + e.getMessage());
		}
	}

	@GetMapping("{masterCustomerId}")
	@PreAuthorize("@securityService.hasPermission('getMasterCustomer')")
	public ResponseEntity<MasterCustomer> getMasterCustomerById(@PathVariable Long masterCustomerId) {
		log.debug("Fetching MasterCustomer with masterCustomerId {} ", masterCustomerId);
		try {
			MasterCustomer masterCustomer = crmService.getMasterCustomer(masterCustomerId);
			log.debug("Fetched MasterCustomer with masterCustomerId {} ", masterCustomerId);
			return new ResponseEntity<MasterCustomer>(masterCustomer, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching MasterCustomer with masterCustomerId {} ",
					masterCustomerId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching MasterCustomer with masterCustomerId - " + masterCustomerId + e.getMessage());
		}
	}

	@PostMapping("")
	@PreAuthorize("@securityService.hasPermission('addMasterCustomer')")
	public ResponseEntity<MasterCustomer> addMasterCustomer(@RequestBody MasterCustomer masterCustomer) {
		log.debug("Adding employee..");
		try {
			masterCustomer = crmService.addMasterCustomer(masterCustomer);
			log.debug("Added addMasterCustomer with id: ", masterCustomer.getId());
			return new ResponseEntity<MasterCustomer>(masterCustomer, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding MasterCustomer." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Adding MasterCustomer." + e.getMessage());
		}
	}

	@PutMapping("")
	@PreAuthorize("@securityService.hasPermission('updateMasterCustomer')")
	public ResponseEntity<MasterCustomer> updateMasterCustomer(@RequestBody MasterCustomer masterCustomer) {
		log.debug("Updating employee..");
		try {
			masterCustomer = crmService.updateMasterCustomer(masterCustomer);
			log.debug("Updated addMasterCustomer with id: ", masterCustomer.getId());
			return new ResponseEntity<MasterCustomer>(masterCustomer, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Updating MasterCustomer." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Updating MasterCustomer." + e.getMessage());
		}
	}

	@GetMapping("ratechart/{masterCustomerId}")
	@PreAuthorize("@securityService.hasPermission('getRateCharts')")
	public ResponseEntity<List<MasterRateChart>> getActiveRateChartsForMaster(@PathVariable Long masterCustomerId) {
		log.debug("Fetching Active rate charts for masterCustomerId {} ", masterCustomerId);
		try {
			List<MasterRateChart> rateCharts = crmService.getActiveRateChartsForMaster(masterCustomerId);
			log.debug("Fetched Active rate charts for masterCustomerId {} ", masterCustomerId);
			return new ResponseEntity<List<MasterRateChart>>(rateCharts, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching Active rate charts for masterCustomerId {} ",
					masterCustomerId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching Active rate charts for masterCustomerId - " + masterCustomerId
							+ e.getMessage());
		}
	}

	@PostMapping("ratechart/{masterCustomerId}")
	@PreAuthorize("@securityService.hasPermission('addRateChart')")
	public ResponseEntity<List<MasterRateChart>> addRateChart(@RequestBody List<MasterRateChart> rateCharts,
			@PathVariable Long masterCustomerId) {
		log.debug("Adding rateCharts for masterCustomerId {}", masterCustomerId);
		try {
			List<MasterRateChart> charts = crmService.addRateChart(rateCharts, masterCustomerId);
			log.debug("Added {} rateCharts with masterCustomerId: ", charts.size(), masterCustomerId);
			return new ResponseEntity<List<MasterRateChart>>(charts, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding rateCharts." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Adding rateCharts." + e.getMessage());
		}
	}

	@PutMapping("ratechart")
	@PreAuthorize("@securityService.hasPermission('updateRateChart')")
	public ResponseEntity<MasterRateChart> updateRateChart(@RequestBody MasterRateChart rateChart) {
		log.debug("Updating rateChart with id {}", rateChart.getId());
		try {
			MasterRateChart chart = crmService.updateRateChart(rateChart);
			log.debug("Updated rateChart with id {} ", chart.getId());
			return new ResponseEntity<MasterRateChart>(chart, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while updating rateChart with id {}", rateChart.getId() + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while updating rateChart" + e.getMessage());
		}
	}

	@GetMapping("customer/{customerId}")
	@PreAuthorize("@securityService.hasPermission('getCustomer')")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long customerId) {
		log.debug("Fetching Customer for customerId {} ", customerId);
		try {
			Customer customer = crmService.getCustomer(customerId);
			log.debug("Fetched customer with ID {} ", customerId);
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching Customer for customerId {} ", customerId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching Customer for customerId - " + customerId + e.getMessage());
		}
	}

	@GetMapping("customer/master/{masterCustomerId}")
	@PreAuthorize("@securityService.hasPermission('getCustomer')")
	public ResponseEntity<List<Customer>> getCustomersByMasterId(@PathVariable Long masterCustomerId) {
		log.debug("Fetching Customers for masterCustomerId {} ", masterCustomerId);
		try {
			List<Customer> customer = crmService.getCustomersByMasterId(masterCustomerId);
			log.debug("Fetched customers with masterCustomerId {} ", masterCustomerId);
			return new ResponseEntity<List<Customer>>(customer, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching Customers for masterCustomerId {} ", masterCustomerId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching Customers for masterCustomerId - " + masterCustomerId + e.getMessage());
		}
	}

	@PostMapping("customer/{masterCustomerId}")
	@PreAuthorize("@securityService.hasPermission('addCustomer')")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer, @PathVariable Long masterCustomerId) {
		log.debug("Adding customer for master {}", masterCustomerId);
		try {
			Customer customer2 = crmService.addCustomer(customer, masterCustomerId);
			log.debug("Added customer for  masterCustomerId: ", masterCustomerId);
			return new ResponseEntity<Customer>(customer2, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding Customer." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while Adding Customer." + e.getMessage());
		}
	}

	@PutMapping("customer")
	@PreAuthorize("@securityService.hasPermission('updateCustomer')")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
		log.debug("Updating customer  {}", customer.getName());
		try {
			Customer customer2 = crmService.updateCustomer(customer);
			log.debug("Updated customer {} ", customer.getName());
			return new ResponseEntity<Customer>(customer2, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while updating Customer." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while updating Customer." + e.getMessage());
		}
	}

	@GetMapping("contract/{contractId}")
	@PreAuthorize("@securityService.hasPermission('getContract')")
	public ResponseEntity<Contract> getContract(@PathVariable Long contractId) {
		log.debug("Fetching Opportunity for contractId {} ", contractId);
		try {
			Contract customer = crmService.getContract(contractId);
			log.debug("Fetched Opportunity with contractId {} ", contractId);
			return new ResponseEntity<Contract>(customer, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching Opportunity for contractId {} ", contractId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching Opportunity for contractId - " + contractId + e.getMessage());
		}
	}

	@GetMapping("contract/all/{customerId}")
	@PreAuthorize("@securityService.hasPermission('getContract')")
	public ResponseEntity<List<Contract>> getAllContractsByCustomerId(@PathVariable Long customerId) {
		log.debug("Fetching all Contracts for customerId {} ", customerId);
		try {
			List<Contract> contracts = crmService.getAllContractsByCustomerId(customerId);
			log.debug("Fetched all Contracts with customerId {} ", customerId);
			return new ResponseEntity<List<Contract>>(contracts, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching all Contracts for customerId {} ", customerId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching all Contracts for contractId - " + customerId + e.getMessage());
		}
	}

	@GetMapping("contract/active/{customerId}")
	@PreAuthorize("@securityService.hasPermission('getContract')")
	public ResponseEntity<List<Contract>> getActiveContractsByCustomerId(@PathVariable Long customerId) {
		log.debug("Fetching Active Contracts for customerId {} ", customerId);
		try {
			List<Contract> contracts = crmService.getActiveContractsByCustomerId(customerId);
			log.debug("Fetched Active Contracts with customerId {} ", customerId);
			return new ResponseEntity<List<Contract>>(contracts, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching Active Contracts for customerId {} ", customerId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching Active Contracts for contractId - " + customerId + e.getMessage());
		}
	}
	
	@PostMapping("contract/{customerId}")
	@PreAuthorize("@securityService.hasPermission('addContract')")
	public ResponseEntity<Contract> addContract(@RequestBody Contract contract, @PathVariable Long customerId) {
		log.debug("Adding contract for customerId {}", customerId);
		try {
			Contract contract2 = crmService.addContract(contract, customerId);
			log.debug("Added contract for  customerId: ", customerId);
			return new ResponseEntity<Contract>(contract2, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding contract." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while Adding contract." + e.getMessage());
		}
	}
	
	@PostMapping("opportunity/{customerId}")
//	@PreAuthorize("@securityService.hasPermission('getCustomer')")
	public ResponseEntity<Opportunity> addOpportunity(@RequestBody Opportunity opportunity,@PathVariable Long customerId){
		log.debug("Adding opportunity for customerId {}",customerId);
		try {
			Opportunity opp2 = crmService.addOpportunity(opportunity,customerId);
			log.debug("Added opportunity for customerId: ",customerId);
			return new ResponseEntity<Opportunity>(opp2,HttpStatus.OK);
		}catch(Exception e) {
			log.error("Erro while Adding Opportunity."+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while Adding opportunity."+e.getMessage());
		}
	}
	
	@PutMapping("contract")
	@PreAuthorize("@securityService.hasPermission('updateContract')")
	public ResponseEntity<Contract> updateContract(@RequestBody Contract contract) {
		log.debug("Updating contract for id {}", contract.getId());
		try {
			Contract contract2 = crmService.updateContract(contract);
			log.debug("Updated contract for  id: ", contract.getId());
			return new ResponseEntity<Contract>(contract2, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Updating contract." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while Updating contract." + e.getMessage());
		}
	}
	
	@PutMapping("opportunity")
//	@PreAuthorize("securityService.hasPermission('updateOpportunity')")
	public ResponseEntity<Opportunity> updateOpportunity(@RequestBody Opportunity opportunity){
		log.debug("Updating opportunity for id {}",opportunity.getId());
		try {
			Opportunity opportunity2 = crmService.updateOpportunity(opportunity);
			log.debug("Updated opportunity for id: ",opportunity.getId());
			return new ResponseEntity<Opportunity>(opportunity2,HttpStatus.OK);
		}catch(Exception e) {
			log.error("Error while updating opportunity."+e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while Updating opportunity."+e.getMessage());
		}
	}
	
	@GetMapping("contract/effortBasedBilling/{contractId}")
	@PreAuthorize("@securityService.hasPermission('getEffortBasedBilling')")
	public ResponseEntity<List<ContractRateChart>> getActiveEffortBasedBillingForContract(@PathVariable Long contractId) {
		log.debug("Fetching active ContractRateChart for contractId {} ", contractId);
		try {
			List<ContractRateChart> effortBasedBillings = crmService.getActiveEffortBasedBillingForContract(contractId);
			log.debug("Fetched active ContractRateChart with contractId {} ", contractId);
			return new ResponseEntity<List<ContractRateChart>>(effortBasedBillings, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching active ContractRateChart for contractId {} ", contractId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching active ContractRateChart for contractId - " + contractId + e.getMessage());
		}
	}
	
	@PostMapping("contract/effortBasedBilling/{contractId}")
	@PreAuthorize("@securityService.hasPermission('addEffortBasedBilling')")
	public ResponseEntity<List<ContractRateChart>> addEffortBasedBillingForContract(@RequestBody List<ContractRateChart> effortBasedBillings,
			@PathVariable Long contractId) {
		log.debug("Adding ContractRateChart for contractId {}", contractId);
		try {
			List<ContractRateChart> charts = crmService.addEffortBasedBillingForContract(effortBasedBillings, contractId);
			log.debug("Added {} ContractRateChart with contractId: ", charts.size(), contractId);
			return new ResponseEntity<List<ContractRateChart>>(charts, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding ContractRateChart." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Adding ContractRateChart." + e.getMessage());
		}
	}

	@PutMapping("contract/effortBasedBilling")
	@PreAuthorize("@securityService.hasPermission('updateEffortBasedBilling')")
	public ResponseEntity<ContractRateChart> updateEffortBasedBillingForContract(@RequestBody ContractRateChart effortBasedBilling) {
		log.debug("Updating effortBasedBilling with id {}", effortBasedBilling.getId());
		try {
			ContractRateChart basedBilling = crmService.updateEffortBasedBillingForContract(effortBasedBilling);
			log.debug("Updated effortBasedBilling with id {} ", basedBilling.getId());
			return new ResponseEntity<ContractRateChart>(basedBilling, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while updating effortBasedBilling with id {}", effortBasedBilling.getId() + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while updating effortBasedBilling" + e.getMessage());
		}
	}
	
	@GetMapping("contract/fixedBilling/{contractId}")
	@PreAuthorize("@securityService.hasPermission('getFixedBilling')")
	public ResponseEntity<List<FixedBilling>> getActiveFixedBillingForContract(@PathVariable Long contractId) {
		log.debug("Fetching active FixedBilling for contractId {} ", contractId);
		try {
			List<FixedBilling> effortBasedBillings = crmService.getActiveFixedBillingForContract(contractId);
			log.debug("Fetched active FixedBilling with contractId {} ", contractId);
			return new ResponseEntity<List<FixedBilling>>(effortBasedBillings, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching active FixedBilling for contractId {} ", contractId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching active FixedBilling for contractId - " + contractId + e.getMessage());
		}
	}
	
	@PostMapping("contract/fixedBilling/{contractId}")
	@PreAuthorize("@securityService.hasPermission('addFixedBilling')")
	public ResponseEntity<List<FixedBilling>> addFixedBillingForContract(@RequestBody List<FixedBilling> fixedBillings,
			@PathVariable Long contractId) {
		log.debug("Adding FixedBilling for contractId {}", contractId);
		try {
			List<FixedBilling> charts = crmService.addFixedBillingForContract(fixedBillings, contractId);
			log.debug("Added {} FixedBilling with contractId: ", charts.size(), contractId);
			return new ResponseEntity<List<FixedBilling>>(charts, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while Adding FixedBilling." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Adding FixedBilling." + e.getMessage());
		}
	}

	@PutMapping("contract/fixedBilling")
	@PreAuthorize("@securityService.hasPermission('updateFixedBilling')")
	public ResponseEntity<FixedBilling> updateFixedBillingForContract(@RequestBody FixedBilling fixedBilling) {
		log.debug("Updating FixedBilling with id {}", fixedBilling.getId());
		try {
			FixedBilling basedBilling = crmService.updateFixedBillingForContract(fixedBilling);
			log.debug("Updated FixedBilling with id {} ", basedBilling.getId());
			return new ResponseEntity<FixedBilling>(basedBilling, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error while updating FixedBilling with id {}", fixedBilling.getId() + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while updating FixedBilling" + e.getMessage());
		}
	}
	
	@GetMapping("opportunity/{opportunityId}")
	@PreAuthorize("@securityService.hasPermission('getContract')")
	public ResponseEntity<Opportunity> getOpportunity(@PathVariable Long opportunityId) {
		log.debug("Fetching Opportunity for opportunityId {} ", opportunityId);
		try {
			Opportunity opportunity = crmService.getOpportunity(opportunityId);
			log.debug("Fetched Opportunity with opportunityId {} ", opportunityId);
			return new ResponseEntity<Opportunity>(opportunity, HttpStatus.OK);
		}

		catch (Exception e) {
			log.error("Error while Fetching Opportunity for opportunityId {} ", opportunityId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching Opportunity for opportunityId - " + opportunityId + e.getMessage());
		}
	}
	
	@GetMapping("opportunity/all/{customerId}")
	@PreAuthorize("@securityService.hasPermission('getCustomer')")
	public ResponseEntity<List<Opportunity>> getAllOpportunityByCustomerId(@PathVariable Long customerId) {
		log.debug("Fetching all Contracts for customerId {} ", customerId);
		try {
			List<Opportunity> opportunties = crmService.getAllOpportunityByCustomerId(customerId);
			return new ResponseEntity<List<Opportunity>>(opportunties, HttpStatus.OK);
		}
		catch (Exception e) {
			log.error("Error while Fetching all Opportunities for customerId {} ", customerId + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Error while Fetching all Opportunities for contractId - " + customerId + e.getMessage());
		}
	}

	
}
