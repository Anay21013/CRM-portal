import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ApiService } from './api.service'; 

@Injectable({
  providedIn: 'root',
})
export class CustomerDataService {
  private masterCustomersSubject = new BehaviorSubject<any[]>([]);
  private customersSubject = new BehaviorSubject<any[]>([]);
  private ratesSubject = new BehaviorSubject<any[]>([]);
  private contractsSubject = new BehaviorSubject<any[]>([]);
  private opportunitySubject = new BehaviorSubject<any[]>([]);
  


  constructor(private apiService: ApiService) {}

  masterCustomers$ = this.masterCustomersSubject.asObservable();
  customers$ = this.customersSubject.asObservable();
  rates$ = this.ratesSubject.asObservable();
  contracts$ = this.contractsSubject.asObservable();
  opportunity$ = this.opportunitySubject.asObservable();

  setMasterCustomers(customers: any[]) {
    this.masterCustomersSubject.next(customers);
  }
  
  setCustomers(customers: any[]) {
    this.customersSubject.next(customers);
  }

  setRates(rates: any[]) {
    this.ratesSubject.next(rates);
  }

  setContracts(contracts: any[]) {
    this.contractsSubject.next(contracts);
  }

  setOpportunity(opportunities: any[]) {
    this.opportunitySubject.next(opportunities);
  }

  addMasterCustomer(customer: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post('api/crm', customer).subscribe(
        (newCustomer) => {
          const currentCustomers = this.masterCustomersSubject.getValue();
          const updatedCustomers = [...currentCustomers, newCustomer];
          this.masterCustomersSubject.next(updatedCustomers);
          resolve(newCustomer);
        },
        (error) => {
          reject(error);
        }
      );
    });
  }

  addCustomer(masterCustomerId: string): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`crm/customer/${masterCustomerId}`).subscribe(
        (newCustomer) => {
          const currentCustomers = this.customersSubject.getValue();
          const updatedCustomers = [...currentCustomers, newCustomer];
          this.customersSubject.next(updatedCustomers);
          resolve(newCustomer);
        },
        (error) => {
          reject(error);
        }
      );
    });
  }

  addRateChart(masterCustomerId: string, rateCharts: any[]): Promise<any[]> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`crm/ratechart/${masterCustomerId}`, rateCharts).subscribe(
        (newRateCharts) => {
          const currentRates = this.ratesSubject.getValue();
          const updatedRates = [...currentRates, ...newRateCharts];
          this.ratesSubject.next(updatedRates);
          resolve(newRateCharts);
        },
        (error) => {
          reject(error);
        }
      );
    });
  }
  addOpportunity(customerId: string, opportunity: any): Promise<any[]> {
    return new Promise((resolve, reject) => {
        this.apiService.post(`crm/opportunity/${customerId}`, opportunity).subscribe(
            (newOpportunity) => {
                const currentOpportunities = this.opportunitySubject.getValue();
                const updatedOpportunities = [...currentOpportunities, newOpportunity];
                this.opportunitySubject.next(updatedOpportunities);
                resolve(newOpportunity);
            },
            (error) => {
                reject(error);
            }
        );
    });
  }

  addContract(customerId: string, contract: any): Promise<any[]> {
    return new Promise((resolve, reject) => {
        this.apiService.post(`crm/contract/${customerId}`, contract).subscribe(
            (newContract) => {
                const currentContracts = this.contractsSubject.getValue();
                const updatedContracts = [...currentContracts, newContract];
                this.opportunitySubject.next(updatedContracts);
                resolve(newContract);
            },
            (error) => {
                reject(error);
            }
        );
    });
  }

  setCustomer(customer: any) {
    const currentCustomers = this.customersSubject.getValue();
    const updatedCustomers = [...currentCustomers, customer];
    this.customersSubject.next(updatedCustomers);
  }
}
