import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { CustomerDataService } from '../../services/sharedMaster.service'; // Correct import path
import { LoaderService } from '../../services/loader.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { ColumnDef, RowActionDef } from '../../common-utils/components/table/table.config';
import { customerTable } from './customer-table';

@Component({
    selector: 'customer-table-component',
    templateUrl: './customer-table.component.html'
})
export class CustomerTableComponent implements OnInit {
    columnDefs: ColumnDef[];
    @Input() rowData: any[] = [];
     masterCustomers: any[] = [];
     customerDetails: any[] = [];
    @Output() addCustomer = new EventEmitter<void>();
    constructor(
      private readonly customerDataService: CustomerDataService,
      private readonly loaderService: LoaderService,
      private readonly toastrService: ToastrService,
      private readonly router: Router,
      ) {}
      
  
    ngOnInit() {
  
      this.customerDataService.customers$.subscribe(customers => {
        this.customerDetails = customers;
        this.rowData = this.customerDetails;
        // console.log(this.rowData)
      });
      this.initTable()
    }
    initTable() {
      this.columnDefs = customerTable({
        cellClicked: (data: any) => this.router.navigate([`/app/crm/customer/${data.id}`])
      });
    }

    openAddCustomerModal() {
      this.addCustomer.emit();
    }
}