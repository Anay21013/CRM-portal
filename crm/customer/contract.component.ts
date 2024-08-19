import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { CustomerDataService } from '../../services/sharedMaster.service'; // Correct import path
import { LoaderService } from '../../services/loader.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { ColumnDef, RowActionDef } from '../../common-utils/components/table/table.config';
import { contractTable } from './contract.table';

@Component({
    selector: 'contract-component',
    templateUrl: './contract.component.html'
})
export class ContractComponent implements OnInit {
    columnDefs: ColumnDef[];
    @Input() rowData: any[] = [];
     masterCustomers: any[] = [];
     contractDetails: any[] = [];
    @Output() addContract = new EventEmitter<void>();
    constructor(
      private readonly customerDataService: CustomerDataService,
      private readonly loaderService: LoaderService,
      private readonly toastrService: ToastrService,
      private readonly router: Router,
      ) {}
  
  
    ngOnInit() {
  
      this.customerDataService.contracts$.subscribe(contracts => {
        this.contractDetails = contracts;
        this.rowData = this.contractDetails;
        // console.log(this.rowData)
      });
      this.initTable()
    }
    initTable() {
      this.columnDefs = contractTable({
        cellClicked: (data: any) => this.router.navigate([`/app/crm/contract/${data.id}`])
      });
    }

    openAddContractModal(){
      this.addContract.emit();
    }
}