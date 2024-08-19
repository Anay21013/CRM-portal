import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { CustomerDataService } from '../../services/sharedMaster.service'; // Correct import path
import { LoaderService } from '../../services/loader.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { ColumnDef, RowActionDef } from '../../common-utils/components/table/table.config';
import { opportunityTable } from './opportunity.table';

@Component({
    selector: 'opportunity-component',
    templateUrl: './opportunity.component.html'
})
export class OpportunityComponent implements OnInit {
    columnDefs: ColumnDef[];
    @Input() rowData: any[] = [];
     masterCustomers: any[] = [];
     opportunityDetails: any[] = [];
     @Output() addOpportunity = new EventEmitter<void>();

    constructor(
      private readonly customerDataService: CustomerDataService,
      private readonly loaderService: LoaderService,
      private readonly toastrService: ToastrService,
      private readonly router: Router,
      ) {}
  
  
    ngOnInit() {
  
      this.customerDataService.opportunity$.subscribe(opportunities => {
        this.opportunityDetails = opportunities;
        this.rowData = this.opportunityDetails;
        // console.log(this.rowData)
      });
      this.initTable()
    }
    initTable() {
      this.columnDefs = opportunityTable({
        cellClicked: (data: any) => this.router.navigate([`/app/crm/opportunity/${data.id}`])
      });
    }
    openAddOpportunityModal(){
      this.addOpportunity.emit();
    }
}