import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private readonly apiService: ApiService) { }

  getUsers(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get('user').subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }
  updateUser(params: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get('user', params).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  deleteUser(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.delete(`user/${id}`).subscribe({
        next: (sucess) => {
          resolve(sucess);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getDepartments(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get('common/departments').subscribe({
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
  getOrganizations(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get('common/organizations').subscribe({
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
  getOrganizationUnits(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get('common/organizationUnits').subscribe({
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

  getUserById(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`user/${id}`).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }
}
