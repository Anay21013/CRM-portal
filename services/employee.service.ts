import { Injectable } from '@angular/core';
import { ApiService } from 'projects/vhub/src/app/services/api.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private readonly apiService: ApiService) { }
  employeeDetails: any;

  getEmployeeDetailsByUserId(userId: number, patchValue: boolean = false): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`employee/${userId}`).subscribe({
        next: (res) => {
          if (patchValue) {
            this.employeeDetails = res;
          }
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getEmployeeDetailsById(employeeId: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`employee/${employeeId}/details`).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getAllEmployee(params: boolean = false): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`employee?showSeparated=${params}`).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getAllEmployeeEmail(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`employee/all/email`).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  convertTempToFTE(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.delete(`employee/convert/${id}`).subscribe({
        next: (sucess) => {
          resolve(sucess);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getAssetDetailByEmployee(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`assetDetail/employee/${id}`).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getFinancialDetailByEmployee(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`employee/financialDetails/${id}`).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getEmployeeReports(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`report/employees`).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  addAsset(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`assetDetail/addAsset`, data).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  updateAsset(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.put(`assetDetail`, data).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  deleteAsset(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.delete(`assetDetail/${id}`).subscribe({
        next: (sucess) => {
          resolve(sucess);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  deleteEmployee(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.delete(`employee/${id}`).subscribe({
        next: (sucess) => {
          resolve(sucess);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  resignEmployee(data: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`employee/intiateSeparation`, data).subscribe({
        next: (sucess) => {
          resolve(sucess);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  addEmployee(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post('employee', params).subscribe({
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

  updateEmployee(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.put('employee/updateEmployeeData', params).subscribe({
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

  inviteEmployee(employee: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`employee/invite`, employee).subscribe({
        next: (sucess) => {
          resolve(sucess);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  activateEmployees(employeeId: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`employee/${employeeId}/activate`).subscribe({
        next: (sucess) => {
          resolve(sucess);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  deactivateEmployees(employeeId: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`employee/${employeeId}/deactivate`).subscribe({
        next: (sucess) => {
          resolve(sucess);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getApprovals(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`approval`).subscribe({
        next: (res) => {
          resolve(res);
        },
        error: (error) => {
          reject(error?.error);
        }
      });
    });
  }

  getApprovers(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`employee/approver`).subscribe({
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
