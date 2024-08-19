import { Injectable } from "@angular/core";
import { ApiService } from "./api.service";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class MasterCustomerService{
  private apiUrl = 'crm/customer/master';
    constructor(
      private readonly apiService: ApiService,
      private readonly http: HttpClient
      ) { }
    getAllMasterCustomers(): Promise<any> {
        return new Promise((resolve, reject) => {
          this.apiService.get(`crm`).subscribe({
            next: (res) => {
              resolve(res);
            },
            error: (error) => {
              reject(error?.error);
            }
          });
        });
    }
    getMasterCustomerDetailsById(customerId: number): Promise<any> {
        return new Promise((resolve, reject) => {
          this.apiService.get(`crm/${customerId}`).subscribe({
            next: (res) => {
              resolve(res);
            },
            error: (error) => {
              reject(error?.error);
            }
          });
        });
      }
      getCustomerDetails(masterCustomerId: string): Promise<any[]> {
        return new Promise((resolve, reject) => {
          this.apiService.get(`${this.apiUrl}/${masterCustomerId}`).subscribe({
            next: (res) => {
              resolve(res);
            },
            error: (error) => {
              reject(error?.error);
            }
          });
        });
      }
      getRateCharts(masterCustomerId:string): Promise<any[]>{
        return new Promise((resolve,reject)=>{
          this.apiService.get(`crm/ratechart/${masterCustomerId}`).subscribe({
            next: (res) => {
              resolve(res);
            },
            error:(error)=>{
              reject(error?.error);
            }
          });
        });
      }
      getCustomer(customerId: string): Promise<any[]> {
        return new Promise((resolve, reject) => {
          this.apiService.get(`crm/customer/${customerId}`).subscribe({
            next: (res) => {
              resolve(res);
            },
            error: (error) => {
              reject(error?.error);
            }
          });
        });
      }
      addMasterCustomer(customer: any): Promise<any> {
        return new Promise((resolve, reject) => {
          this.apiService.post(`crm`, customer).subscribe({
            next: (res) => resolve(res),
            error: (error) => reject(error?.error)
          });
        });
      }
      addCustomer(masterCustomerId: string, customer: any):Promise<any>{
        return new Promise((resolve,reject)=>{
          this.apiService.post(`crm/customer/${masterCustomerId}`, customer).subscribe({
            next: (res) => {
              resolve(res);
            },
            error:(error)=>{
              reject(error?.error);
            }
          });
        });
      }

      addRateChart(masterCustomerId: string, rateCharts: any[]): Promise<any> {
        return new Promise((resolve, reject) => {
          this.apiService.post(`crm/ratechart/${masterCustomerId}`, rateCharts).subscribe({
            next: (res) => {
              resolve(res);
            },
            error: (error) => {
              reject(error?.error);
            }
          });
        });
      }
      updateMasterCustomer(masterCustomer: any): Promise<any> {
        return new Promise((resolve, reject) => {
          this.apiService.put('crm', masterCustomer).subscribe({
            next: (res) => resolve(res),
            error: (error) => reject(error?.error)
          });
        });
      }

      updateCustomer(customer: any):Promise<any>{
        return new Promise((resolve,reject)=>{
          this.apiService.put('crm/customer',customer).subscribe({
            next:(res)=>resolve(res),
            error: (error)=>reject(error?.error)
          });
        });
      }
}
