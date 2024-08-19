import { Component, EventEmitter, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MasterCustomerService } from '../services/master.service';
import { LoaderService } from '../services/loader.service';
import { CustomerDataService } from '../services/sharedMaster.service';

@Component({
  selector: 'app-add-master-customer',
  templateUrl: './add-master-customer.component.html',
  styleUrls: ['./add-master-customer.component.css']
})
export class AddMasterCustomerComponent {
  @Output() close = new EventEmitter<void>();
  customer = {
    id:'',
    name: '',
    phone: '',
    email: '',
    addressLine1: '',
    addressLine2: '',
    city: '',
    state: '',
    zip: '',
    country: '',
    paymentTerms: 1,
    createdBy: '',
    lastModifiedBy:''
  };

  constructor(
    private readonly masterCustomerService: MasterCustomerService,
    private readonly toastrService: ToastrService,
    private readonly loaderService: LoaderService,
    private readonly customerDataService: CustomerDataService
  ) {}

  onSubmit(): void {
    this.loaderService.startLoading();
    this.masterCustomerService.addMasterCustomer(this.customer).then(() => {
      this.toastrService.success('Master customer added successfully');
        this.customerDataService.addMasterCustomer(this.customer);
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