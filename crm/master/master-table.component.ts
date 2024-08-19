import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { CustomerDataService } from '../../services/sharedMaster.service'; // Correct import path
import { LoaderService } from '../../services/loader.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { ColumnDef, RowActionDef } from '../../common-utils/components/table/table.config';
import { masterTable } from './master-table';

@Component({
  selector: 'master-table-component',
  templateUrl: './master-table.component.html'
})
export class MasterTableComponent implements OnInit {
  columnDefs: ColumnDef[];
  @Input() rowData: any[] = [];
   masterCustomers: any[] = [];
   showAddCustomerModal = false;
   @Output() addMaster = new EventEmitter<void>();
  constructor(
    private readonly customerDataService: CustomerDataService,
    private readonly loaderService: LoaderService,
    private readonly toastrService: ToastrService,
    private readonly router: Router,
    ) {}

//   @Input() data: any[] = [];

  ngOnInit() {
    // console.log('Initial Table Data:', this.data);

    this.customerDataService.masterCustomers$.subscribe(customers => {
      this.masterCustomers = customers;
    //   console.log('Updated Table Data:', this.masterCustomers);
      this.rowData = this.masterCustomers;
      // console.log(this.rowData)
    });
    this.initTable()
  }
  initTable() {
    this.columnDefs = masterTable({
      cellClicked: (data: any) => this.router.navigate([`/app/crm/master/${data.id}`])
    });
  }
  // openAddCustomerModal() {
  //   this.showAddCustomerModal = true;
  // }

  closeAddCustomerModal() {
    this.showAddCustomerModal = false;
  }

  openAddCustomerModal() {
    this.addMaster.emit();
  }
}
