import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MasterCustomerService } from '../../services/master.service';
import { LoaderService } from '../../services/loader.service';
import { ToastrService } from 'ngx-toastr';
import { CustomerDataService } from '../../services/sharedMaster.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-details',
  templateUrl: './customerList.component.html',
  styleUrls: ['./customerList.component.css']
})
export class CustomerListComponent implements OnInit {
  masterCustomerId: string;
  masterCustomerDetails: any;
  customerDetails: any[] = [];
  masterCustomers: any[] = [];
  rates: any[] = [];
  showAddCustomerModal: boolean = false;
  showAddRateChartModal: boolean = false;
  showEditMasterCustomerModal: boolean = false; 

  constructor(
    private route: ActivatedRoute,
    private masterCustomerService: MasterCustomerService,
    private loaderService: LoaderService,
    private readonly customerDataService: CustomerDataService,
    private toastrService: ToastrService,
    private readonly router: Router,
  ) {}

  ngOnInit(): void {
    this.masterCustomerId = this.route.snapshot.paramMap.get('masterCustomerId');
    if (this.masterCustomerId) {
      this.fetchDetails();
    }
  }

  fetchDetails(): void {
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
      this.customerDataService.setMasterCustomers(this.masterCustomers);
    }).catch((error) => {
      this.loaderService.stopLoading();
      this.toastrService.error("Error fetching customer data");
    });

    this.masterCustomerService.getCustomerDetails(this.masterCustomerId).then((res) => {
      this.customerDetails = res.map(customer => ({
        endDate: customer.endDate || null,
        deleted: customer.deleted || false,
        createdBy: customer.createdBy || 'unknown',
        createdDatetime: customer.createdDatetime || new Date().toISOString(),
        lastModifiedBy: customer.lastModifiedBy || 'unknown',
        lastModifiedDatetime: customer.lastModifiedDatetime || new Date().toISOString(),
        id: customer.id || null,
        orgId: customer.orgId || null,
        name: customer.name || '',
        phone: customer.phone || '',
        email: customer.email || '',
        addressLine1: customer.addressLine1 || '',
        addressLine2: customer.addressLine2 || '',
        city: customer.city || '',
        state: customer.state || '',
        zip: customer.zip || '',
        country: customer.country || '',
      }));
      for (let customer of this.masterCustomers) {
        if (customer.id == parseInt(this.masterCustomerId)) {
          this.masterCustomerDetails = customer;
          break;
        }
      }
      this.customerDataService.setCustomers(this.customerDetails);
      this.loaderService.stopLoading();
    }).catch((error) => {
      this.loaderService.stopLoading();
      this.toastrService.error("Error fetching customer data");
    });

    this.masterCustomerService.getRateCharts(this.masterCustomerId).then((res) => {
      this.rates = res.map(rate => ({
        endDate: rate.endDate || null,
        deleted: rate.deleted || false,
        createdBy: rate.createdBy || 'unknown',
        createdDatetime: rate.createdDatetime || new Date().toISOString(),
        lastModifiedBy: rate.lastModifiedBy || 'unknown',
        lastModifiedDatetime: rate.lastModifiedDatetime || new Date().toISOString(),
        id: rate.id || null,
        orgId: rate.orgId || null,
        roleName: rate.roleName || null,
        roleRate: rate.roleRate || null,
        roleRateFrequency: rate.roleRateFrequency || null,
        currencyCode: rate.currencyCode || null,
        startDate: rate.startDate || new Date().toISOString()
      }));
      this.customerDataService.setRates(this.rates);
      this.loaderService.stopLoading();
    });
  }

  getPaymentTermsLabel(paymentTerms: number): string {
    switch (paymentTerms) {
      case 1: return '7 days';
      case 2: return '15 days';
      case 3: return '30 days';
      case 4: return '45 days';
      case 5: return '60 days';
      case 6: return '90 days';
      default: return '';
    }
  }

  updateMasterCustomer() {
    this.loaderService.startLoading();
    this.masterCustomerService.updateMasterCustomer(this.masterCustomerDetails).then((updatedCustomer) => {
      this.masterCustomerDetails = updatedCustomer;
      this.loaderService.stopLoading();
      this.toastrService.success('Master customer updated successfully');
      this.closeEditMasterCustomerModal();
    }).catch((error) => {
      this.loaderService.stopLoading();
      this.toastrService.error('Error updating master customer');
    });
  }

  openAddCustomerModal() {
    this.showAddCustomerModal = true;
  }

  closeAddCustomerModal() {
    this.showAddCustomerModal = false;
  }

  openAddRateChartModal(){
    this.showAddRateChartModal = true;
  }

  closeAddRateChartModal(){
    this.showAddRateChartModal = false;
  }

  openEditMasterCustomerModal() {
    this.showEditMasterCustomerModal = true;
  }

  closeEditMasterCustomerModal() {
    this.showEditMasterCustomerModal = false;
  }
}
