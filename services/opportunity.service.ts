import { Injectable } from "@angular/core";
import { ApiService } from "./api.service";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class OpportunityService{
    private apiUrl = 'crm/opportunity/all';
    constructor(
        private readonly apiService: ApiService,
        private readonly http: HttpClient
    ) { }
    getAllOpportunityByCustomerId(customerId:string):Promise<any[]>{
        return new Promise((resolve,reject)=>{
          this.apiService.get(`crm/opportunity/all/${customerId}`).subscribe({
            next: (res) => {
              resolve(res);
            },
            error: (error) => {
              reject(error?.error);
            }
          });
        });
    }
    addOpportunity(customerId: string, opportunity: any[]): Promise<any> {
      return new Promise((resolve, reject) => {
        this.apiService.post(`crm/opportunity/${customerId}`, opportunity).subscribe({
          next: (res) => {
            resolve(res);
          },
          error: (error) => {
            reject(error?.error);
          }
        });
      });
    }

    getOpportunity(oppoertunityId:string):Promise<any[]>{
      return new Promise((resolve,reject)=>{
        this.apiService.get(`crm/opportunity/${oppoertunityId}`).subscribe({
          next:(res)=>{
            resolve(res);
          },
          error:(error)=>{
            reject(error?.error);
          }
        });
      });
    }

    updateOpportunity(opportunity:any):Promise<any> {
      return new Promise((resolve,reject)=>{
        this.apiService.put(`crm/opportunity`,opportunity).subscribe({
          next:(res)=>{
            resolve(res);
          },
          error:(error)=>{
            reject(error?.error);
          }
        });
      });
    }
}