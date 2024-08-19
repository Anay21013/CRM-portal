import { Component, EventEmitter, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MasterCustomerService } from '../services/master.service';
import { LoaderService } from '../services/loader.service';
import { CustomerDataService } from '../services/sharedMaster.service';
import { ActivatedRoute } from '@angular/router';
import { OpportunityService } from '../services/opportunity.service';

@Component({
    selector: 'app-add-opportunity',
    templateUrl: './add-opportunity.component.html',
    styleUrls: ['./add-opportunity.component.css']
})
export class AddOpportunity {
    @Output() close = new EventEmitter<void>();
    opportunity = {
        name:'',
        owner:'',
        type:'',
        won:'',
        lost:'',
        receievedDate:''
    }
    customerId:string;
    constructor(
        private readonly masterCustomerService: MasterCustomerService,
        private readonly toastrService: ToastrService,
        private readonly loaderService: LoaderService,
        private readonly customerDataService: CustomerDataService,
        private readonly route: ActivatedRoute,
        private readonly opportunityService: OpportunityService
    ){
        this.customerId = this.route.snapshot.paramMap.get('customerId');
    }
    
    onSubmit():void{
        this.loaderService.startLoading();
        this.customerDataService.addOpportunity(this.customerId,this.opportunity).then((newOpportunities)=>{
            this.toastrService.success('Opportunity added successfully');
            this.customerDataService.setOpportunity(newOpportunities);
            this.loaderService.stopLoading();
            this.closeModal();
        }).catch((error) => {
            this.loaderService.stopLoading();
            this.toastrService.error('Error adding opportunity');
        });
    }

    closeModal(): void {
        this.close.emit();
    }
}
