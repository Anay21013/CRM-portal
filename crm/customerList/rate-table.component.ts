import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { CustomerDataService } from '../../services/sharedMaster.service'; // Correct import path
import { LoaderService } from '../../services/loader.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { ColumnDef, RowActionDef } from '../../common-utils/components/table/table.config';
import { rateTable } from './rate-table';

@Component({
    selector: 'rate-table-component',
    templateUrl: './rate-table.component.html'
})
export class RateTableComponent implements OnInit {
    columnDefs: ColumnDef[];
    @Input() rowData: any[] = [];
     rateCharts: any[] = [];

     @Output() addRate = new EventEmitter<void>();
    constructor(
      private readonly customerDataService: CustomerDataService,
      private readonly loaderService: LoaderService,
      private readonly toastrService: ToastrService,
      private readonly router: Router,
      ) {}
  
  //   @Input() data: any[] = [];
  
    ngOnInit() {
      // console.log('Initial Table Data:', this.data);
  
      this.customerDataService.rates$.subscribe(rates => {
        this.rateCharts = rates;
      //   console.log('Updated Table Data:', this.masterCustomers);
        this.rowData = this.rateCharts;
        // console.log(this.rowData)
      });
      this.initTable()
    }
    initTable() {
      this.columnDefs = rateTable({
        cellClicked: (data: any) => this.router.navigate([`/app/crm/ratechart/${data.masterCustomer}`])
      });
    }
    openAddRateChartModal() {
      this.addRate.emit();
    }
}