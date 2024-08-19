import { Component, OnInit, ViewChild, TemplateRef  } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { LoaderService } from '../../services/loader.service';
import { MasterCustomerService } from '../../services/master.service';
import { CustomerDataService } from '../../services/sharedMaster.service'; // Correct import path
import { Router } from '@angular/router';
import { BreadcrumbService } from 'xng-breadcrumb';

@Component({
  selector: 'app-customer',
  templateUrl: './master.component.html',
  styleUrls: ['./master.component.css']
})
export class MasterComponent implements OnInit {
  masterCustomers: any[] = [];
  showAddCustomerModal: boolean = false;
//   masterCustomerTableConfig = MasterCustomerTableConfig;

  constructor(
    private readonly masterCustomerService: MasterCustomerService,
    private readonly toastrService: ToastrService,
    private readonly loaderService: LoaderService,
    private readonly customerDataService: CustomerDataService,
    private readonly router: Router,
    private readonly breadcrumbService : BreadcrumbService
  ) {
    this.breadcrumbService.set('@crm','Customer Management');
  }

  ngOnInit(): void {
    this.getMasterCustomers();
  }

  getMasterCustomers(): void {
    this.loaderService.startLoading();
    this.masterCustomerService.getAllMasterCustomers().then((res) => {
      this.masterCustomers = res.map(customer => ({
        endDate: customer.endDate || null,
        deleted: customer.deleted || false,
        createdBy: customer.createdBy || 'unknown',
        createdDatetime: customer.createdDatetime || new Date().toISOString(),
        lastModifiedBy: customer.lastModifiedBy || 'unknown',
        lastModifiedDatetime: customer.lastModifiedDatetime || new Date().toISOString(),
        id: customer.id || null,
        orgId: customer.orgId || null,
        name: customer.name || '',
        paymentTerms: customer.paymentTerms || 0,
        phone: customer.phone || '',
        email: customer.email || '',
        addressLine1: customer.addressLine1 || '',
        addressLine2: customer.addressLine2 || '',
        city: customer.city || '',
        state: customer.state || '',
        zip: customer.zip || '',
        country: customer.country || '',
        customers: customer.customers || null,
        rateChart: customer.rateChart || null,
      }));
      this.customerDataService.setMasterCustomers(this.masterCustomers); // Update the shared service
      this.loaderService.stopLoading();
    }).catch((error) => {
      this.loaderService.stopLoading();
      this.toastrService.error("Error fetching customer data");
    });
  }
  openAddCustomerModal() {
    this.showAddCustomerModal = true;
  }

  closeAddCustomerModal() {
    this.showAddCustomerModal = false;
  }
}

