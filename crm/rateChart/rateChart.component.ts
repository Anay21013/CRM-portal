import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MasterCustomerService } from '../../services/master.service';
import { LoaderService } from '../../services/loader.service';
import { ToastrService } from 'ngx-toastr';
import { CustomerDataService } from '../../services/sharedMaster.service';

@Component({
    selector: 'rate-details',
    templateUrl: './rateChart.component.html'
})
export class RateComponent implements OnInit {
    
    ngOnInit(): void {


        
    }
    
}