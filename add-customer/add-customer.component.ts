import { Component, EventEmitter, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MasterCustomerService } from '../services/master.service';
import { LoaderService } from '../services/loader.service';
import { CustomerDataService } from '../services/sharedMaster.service';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-add-customer',
    templateUrl: './add-customer.component.html',
    styleUrls: ['./add-customer.component.css']
})
export class AddCustomerComponent {
    @Output() close = new EventEmitter<void>();
    customer = {
        id: '',  
        name: '',
        phone: '',
        email: '',
        addressLine1: '',
        addressLine2: '',
        city: '',
        state: '',
        zip: '',
        country: '',
        createdBy: '',
        lastModifiedBy: ''
    };
    masterCustomerId: string;

    constructor(
        private readonly masterCustomerService: MasterCustomerService,
        private readonly toastrService: ToastrService,
        private readonly loaderService: LoaderService,
        private readonly customerDataService: CustomerDataService,
        private readonly route: ActivatedRoute
    ) {
        this.masterCustomerId = this.route.snapshot.paramMap.get('masterCustomerId');
    }

    onSubmit(): void {
        this.loaderService.startLoading();
        this.customer.id = '';  
        this.masterCustomerService.addCustomer(this.masterCustomerId, this.customer).then((newCustomer) => {
            this.toastrService.success('Customer added successfully');
            this.customerDataService.addCustomer(newCustomer);
            // this.customerDataService.setCustomer(newCustomer);
            this.loaderService.stopLoading();
            this.closeModal();
        }).catch((error) => {
            this.loaderService.stopLoading();
            this.toastrService.error('Error adding master customer');
        });
    }

    closeModal(): void {
        this.close.emit();
    }
}
