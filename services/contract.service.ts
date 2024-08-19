import { Injectable } from "@angular/core";
import { ApiService } from "./api.service";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class ContractService{
    private apiUrl = 'crm/contract/all';
    constructor(
        private readonly apiService: ApiService,
        private readonly http: HttpClient
    ) { }
    getAllContractsByCustomerId(customerId:string):Promise<any[]>{
        return new Promise((resolve,reject)=>{
          this.apiService.get(`crm/contract/all/${customerId}`).subscribe({
            next: (res) => {
              resolve(res);
            },
            error: (error) => {
              reject(error?.error);
            }
          });
        });
    }

    getContract(contractId:string):Promise<any[]>{
      return new Promise((resolve,reject)=>{
        this.apiService.get(`crm/contract/${contractId}`).subscribe({
          next: (res) => {
            resolve(res);
          },
          error: (error) => {
            reject(error?.error);
          }
        });
      });
    }

    addContract(customerId: string, contract: any[]): Promise<any> {
      return new Promise((resolve, reject) => {
        this.apiService.post(`crm/contract/${customerId}`, contract).subscribe({
          next: (res) => {
            resolve(res);
          },
          error: (error) => {
            reject(error?.error);
          }
        });
      });
    }

    updateContract(contract:any):Promise<any>{
      return new Promise((resolve,reject)=>{
        this.apiService.put(`crm/contract`,contract).subscribe({
          next:(res)=>{
            resolve(res);
          },
          error:(error)=>{
            reject(error?.error);
          }
        });
      });
    }

    getActiveEffortBasedBillingForContract(contractId:string):Promise<any>{
      return new Promise((resolve,reject)=>{
        this.apiService.get(`crm/contract/effortBasedBilling/${contractId}`).subscribe({
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