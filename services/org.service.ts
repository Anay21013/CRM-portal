import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class OrgService {

  constructor(private readonly apiService: ApiService) { }

  getOrganization(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`org`).subscribe({
        next: (res) => {
          if (res) {
            resolve(res);
          }
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getOrganizationById(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`org/${id}`).subscribe({
        next: (res) => {
          if (res) {
            resolve(res);
          }
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getOrgLocations(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`org/orgLocation`).subscribe({
        next: (res) => {
          if (res) {
            resolve(res);
          }
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getOrgLocationById(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`org/orgLocation/${id}`).subscribe({
        next: (res) => {
          if (res) {
            resolve(res);
          }
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  addOrgLocation(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post('org/orgLocation', params).subscribe({
        next: (res) => {
          if (res) {
            resolve(res);
          }
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  updateOrgLocation(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.put('org/orgLocation', params).subscribe({
        next: (res) => {
          if (res) {
            resolve(res);
          }
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getOrgLocationConfigByLocId(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`org/orgLocationConfig/${id}`).subscribe({
        next: (res) => {
          if (res) {
            resolve(res);
          }
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  addOrgLocationConfig(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post('org/orgLocationConfig', params).subscribe({
        next: (res) => {
          if (res) {
            resolve(res);
          }
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  updateOrgLocationConfig(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.put('org/orgLocationConfig', params).subscribe({
        next: (res) => {
          if (res) {
            resolve(res);
          }
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }
  
}
