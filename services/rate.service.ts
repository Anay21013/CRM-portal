import { Injectable } from "@angular/core";
import { ApiService } from "./api.service";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class RateService{
    constructor(
        private readonly apiService : ApiService,
        private readonly http : HttpClient
    ){}
    
}