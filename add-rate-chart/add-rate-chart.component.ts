import { Component, EventEmitter, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MasterCustomerService } from '../services/master.service';
import { LoaderService } from '../services/loader.service';
import { CustomerDataService } from '../services/sharedMaster.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-rate-chart',
  templateUrl: './add-rate-chart.component.html',
  styleUrls: ['./add-rate-chart.component.css']
})
export class AddRateComponent {
  @Output() close = new EventEmitter<void>();
  rate = {
    orgId: '',
    roleName: '',
    roleRate: '',
    currencyCode: '',
    roleRateFrequency: '',
    createdBy: ''
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
    this.customerDataService.addRateChart(this.masterCustomerId, [this.rate]).then((newRateCharts) => {
      this.toastrService.success('Rate chart added successfully');
      this.customerDataService.setRates(newRateCharts);
      this.loaderService.stopLoading();
      this.closeModal();
    }).catch((error) => {
      this.loaderService.stopLoading();
      this.toastrService.error('Error adding rate chart');
    });
  }

  closeModal(): void {
    this.close.emit();
  }
}