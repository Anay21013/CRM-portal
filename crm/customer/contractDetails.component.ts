import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MasterCustomerService } from '../../services/master.service';
import { LoaderService } from '../../services/loader.service';
import { ToastrService } from 'ngx-toastr';
import { ContractService } from '../../services/contract.service';
import { CustomerDataService } from '../../services/sharedMaster.service';
import { throwDialogContentAlreadyAttachedError } from '@angular/cdk/dialog';

@Component({
    selector: 'app-contract-detail',
    templateUrl: './contractDetails.component.html',
    styleUrls: ['./contractDetails.component.css']
})
export class ContractDetailsComponent implements OnInit{
    contractId: string;
    contractDetails: any;
    effortBasedBilling:any[];
    showEditContractModal: boolean = false;
    constructor(
        private route: ActivatedRoute,
        private masterCustomerService: MasterCustomerService,
        private loaderService: LoaderService,
        private toastrService: ToastrService,
        private readonly customerDataService: CustomerDataService,
        private contractService: ContractService
    ){}
    ngOnInit(): void {
        this.contractId = this.route.snapshot.paramMap.get('contractId');
        if(this.contractId){
            this.fetchContractDetails();
            this.getActiveEffortBasedBillingForContract();
        }
    }
    fetchContractDetails(){
        this.loaderService.startLoading();
        this.contractService.getContract(this.contractId).then((res)=>{
            this.contractDetails = res;
            this.loaderService.stopLoading();
        }).catch((error)=>{
            this.loaderService.stopLoading();
            this.toastrService.error('Error fetching contract details');
        })
    }
    updateContract(){
        this.loaderService.startLoading();
        this.contractService.updateContract(this.contractDetails).then((updatedContract)=>{
            this.contractDetails = updatedContract;
            this.loaderService.stopLoading();
            this.toastrService.success('Contract updated successfully');
            this.closeEditContractModal();
        }).catch((error)=>{
            this.loaderService.stopLoading();
            this.toastrService.error('Error updating contract');
        })
    }
    openEditContractModal(){
        this.showEditContractModal = true;
    }
    closeEditContractModal(){
        this.showEditContractModal = false;
    }

    getActiveEffortBasedBillingForContract(){
        this.loaderService.startLoading();
        this.contractService.getActiveEffortBasedBillingForContract(this.contractId).then((billing)=>{
            this.effortBasedBilling = billing;
            this.loaderService.stopLoading();
        }).catch((error)=>{
            this.loaderService.stopLoading();
            this.toastrService.error('Error fetching Effort Based Billing');
        })
    }

    
}