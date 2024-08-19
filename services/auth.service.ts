import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Router } from '@angular/router';
import { LovService } from './lov.service';
import * as CryptoJS from 'crypto-js';
import { environment } from '../../environments/environment';
import { RoleService } from './role.service';
import { EmployeeService } from './employee.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private readonly apiService: ApiService,
    private readonly router: Router,
    private readonly roleService: RoleService,
    private readonly employeeService: EmployeeService,
    private readonly lovService: LovService
  ) { }

  get isLoggedIn(): boolean {
    return !!JSON.parse(localStorage.getItem('authentication'))?.token;
  }

  get authentication() {
    if (this.isLoggedIn) {
      return JSON.parse(localStorage.getItem('authentication'));
    }
    return null;
  }

  encrypt(data: string): string {
    return CryptoJS.AES.encrypt(data, environment.token_secret_key).toString();
  }

  decrypt(encryptedData: string): string {
    return CryptoJS.AES.decrypt(encryptedData, environment.token_secret_key).toString(CryptoJS.enc.Utf8);
  }

  login(body: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post('auth/signin', body).subscribe({
        next(success) {
          resolve(success);
        },
        error(error) {
          reject(error?.error);
        }
      });
    });
  }

  signup(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post('signup', params).subscribe({
        next: (res: any) => {
          if (res) {
            resolve(res);
          }
        }, error: (error: any) => {
          reject(error?.error);
        }
      });
    });
  }

  getAllManagers(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get('user/managers').subscribe({
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

  updatePassword(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post('auth/updatePassword', params).subscribe({
        next: (res: any) => {
          resolve(res);
        }, error: (error: any) => {
          reject(error?.error);
        }
      });
    });
  }

  forgotPassword(email: string): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`auth/resetPasswordCode/${email}`).subscribe({
        next: (res: any) => {
          resolve(res);
        }, error: (error: any) => {
          reject(error?.error);
        }
      });
    });
  }

  resetPassword(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`auth/resetPassword`, params).subscribe({
        next: (res: any) => {
          resolve(res);
        }, error: (error: any) => {
          reject(error?.error);
        }
      });
    });
  }

  public hasPermissionForModule(module: string): boolean {
    let isAuthorised = false;
    if (module && this.roleService.permissions?.findIndex((permission: any) => permission.name == module) > -1) {
      isAuthorised = true;
    }
    return isAuthorised;
  }

  logout() {
    localStorage.clear();
    this.employeeService.employeeDetails = null;
    this.lovService.cleardata();
    this.router.navigate(['app/auth/login']);
  }
}
