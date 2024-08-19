import { Component, EventEmitter, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MasterCustomerService } from '../services/master.service';
import { LoaderService } from '../services/loader.service';
import { CustomerDataService } from '../services/sharedMaster.service';
import { ActivatedRoute } from '@angular/router';
import { ContractService } from '../services/contract.service';

@Component({
    selector: 'app-add-contract',
    templateUrl: './add-contract.component.html',
    styleUrls: ['./add-contract.component.css']
})
export class AddContract {
    @Output() close = new EventEmitter<void>();
    contract = {
        name:'',
        owner:'',
        type:'',
        startDate:''
    }
    customerId:string;
    constructor(
        private readonly masterCustomerService: MasterCustomerService,
        private readonly toastrService: ToastrService,
        private readonly loaderService: LoaderService,
        private readonly customerDataService: CustomerDataService,
        private readonly route: ActivatedRoute,
        private readonly contractService: ContractService
    ){
        this.customerId = this.route.snapshot.paramMap.get('customerId');
    }
    
    onSubmit():void{
        this.loaderService.startLoading();
        this.customerDataService.addContract(this.customerId,this.contract).then((newContracts)=>{
            this.toastrService.success('Contract added successfully');
            this.customerDataService.setContracts(newContracts);
            this.loaderService.stopLoading();
            this.closeModal();
        }).catch((error) => {
            this.loaderService.stopLoading();
            this.toastrService.error('Error adding contract');
        });
    }

    closeModal(): void {
        this.close.emit();
    }
}
