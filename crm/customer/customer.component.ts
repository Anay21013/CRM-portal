import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MasterCustomerService } from '../../services/master.service';
import { LoaderService } from '../../services/loader.service';
import { ToastrService } from 'ngx-toastr';
import { ContractService } from '../../services/contract.service';
import { CustomerDataService } from '../../services/sharedMaster.service';
import { OpportunityService } from '../../services/opportunity.service';

@Component({
  selector: 'app-customer-detail',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerDetailComponent implements OnInit {
    customerId: string;
    customerDetails: any;
    contractDetails: any[]=[];
    opportunityDetails: any[]=[];
    showAddOpportunityModal: boolean = false;
    showAddContractModal: boolean = false;
    showEditCustomerModal: boolean = false;
    constructor(
        private route: ActivatedRoute,
        private masterCustomerService: MasterCustomerService,
        private loaderService: LoaderService,
        private toastrService: ToastrService,
        private readonly customerDataService: CustomerDataService,
        private contractService: ContractService,
        private opportunityService : OpportunityService
    ){}
    ngOnInit(): void {
        this.customerId = this.route.snapshot.paramMap.get('customerId');
        if (this.customerId) {
          this.fetchCustomerDetails();
          this.fetchContractDetails();
          this.fetchOpportunityDetails();
        }
    }
    fetchCustomerDetails(): void {
        this.loaderService.startLoading();
        this.masterCustomerService.getCustomer(this.customerId).then((res) => {
          this.customerDetails = res;
          console.log(this.customerDetails)
          this.loaderService.stopLoading();
        }).catch((error) => {
          this.loaderService.stopLoading();
          this.toastrService.error("Error fetching customer details");
        });
    }
    fetchContractDetails():void{
        this.loaderService.startLoading();
        this.contractService.getAllContractsByCustomerId(this.customerId).then((res)=>{
            this.contractDetails = res;
            this.customerDataService.setContracts(this.contractDetails);
            this.loaderService.stopLoading();
        }).catch((error)=>{
            this.loaderService.stopLoading();
            this.toastrService.error("Error fetching contract details");
        })
    }
    fetchOpportunityDetails():void{
        this.loaderService.startLoading();
        this.opportunityService.getAllOpportunityByCustomerId(this.customerId).then((res)=>{
            this.opportunityDetails = res;
            this.customerDataService.setOpportunity(this.opportunityDetails);
            console.log(this.opportunityDetails);
            this.loaderService.stopLoading();
        }).catch((error)=>{
            this.loaderService.stopLoading();
            this.toastrService.error("Error fetching opportunity details");
        })
    }

    updateCustomer(){
        this.loaderService.startLoading();
        this.masterCustomerService.updateCustomer(this.customerDetails).then((updatedCustomer)=>{
            this.customerDetails = updatedCustomer;
            this.loaderService.stopLoading();
            this.toastrService.success('Customer updated successfully');
            this.closeEditCustomerModal();
        }).catch((error)=>{
            this.loaderService.stopLoading();
            this.toastrService.error('Error updating customer');
        })
    }

    openAddOpportunityModal() {
        this.showAddOpportunityModal = true;
    }
    closeAddOpportunityModal() {
        this.showAddOpportunityModal = false;
    }
    openAddContractModal() {
        this.showAddContractModal = true;
    }
    closeAddContractModal() {
        this.showAddContractModal = false;
    }
    openEditCustomerModal(){
        this.showEditCustomerModal = true;
    }
    closeEditCustomerModal(){
        this.showEditCustomerModal = false;
    }
}