import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MasterCustomerService } from '../../services/master.service';
import { LoaderService } from '../../services/loader.service';
import { ToastrService } from 'ngx-toastr';
import { OpportunityService } from '../../services/opportunity.service';
import { CustomerDataService } from '../../services/sharedMaster.service';

@Component({
    selector:'app-opportunity-detail',
    templateUrl: './opportunityDetails.component.html',
    styleUrls: ['./opportunityDetails.component.css']
})
export class OpportunityDetailsComponent implements OnInit{
    opportunityId: string;
    opportunityDetails:any;
    showEditOpportunityModal:boolean =  false;
    constructor(
        private route: ActivatedRoute,
        private masterCustomerService: MasterCustomerService,
        private loaderService: LoaderService,
        private toastrService: ToastrService,
        private readonly customerDataService: CustomerDataService,
        private opportunityService: OpportunityService
    ){}
    ngOnInit(): void {
        this.opportunityId = this.route.snapshot.paramMap.get('opportunityId');
        if(this.opportunityId){
            this.fetchOpportunityDetails();
        }
    }
    fetchOpportunityDetails(){
        this.loaderService.startLoading();
        this.opportunityService.getOpportunity(this.opportunityId).then((res)=>{
            this.opportunityDetails = res;
            this.loaderService.stopLoading();
        }).catch((error)=>{
            this.loaderService.stopLoading();
            this.toastrService.error("Error fetching opportunity details");
        })
    }
    updateOpportunity(){
        this.loaderService.startLoading();
        this.opportunityService.updateOpportunity(this.opportunityDetails).then((updatedOpportunity)=>{
            this.opportunityDetails = updatedOpportunity;
            this.loaderService.stopLoading();
            this.toastrService.success('Opportunity updated successfully');
            this.closeEditOpportunityModal();
        }).catch((error)=>{
            this.loaderService.stopLoading();
            this.toastrService.error('Error updating opportunity');
        })
    }
    openEditOpportunityModal(){
        this.showEditOpportunityModal = true;
    }
    closeEditOpportunityModal(){
        this.showEditOpportunityModal = false;
    }
}