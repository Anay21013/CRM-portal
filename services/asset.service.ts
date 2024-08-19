import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  constructor(private readonly apiService: ApiService) { }

  addAsset(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post('assetDetail/addAsset', params).subscribe({
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

  getAssets(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`assetDetail`).subscribe({
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

  getAssetReports(params: any = null): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.put(`report/asset`, params).subscribe({
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

  getAssetById(id: number): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get(`assetDetail/${id}`).subscribe({
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

  updateAsset(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.put('assetDetail', params).subscribe({
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

  allocateAsset(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`assetDetail/allocate`, params).subscribe({
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

  bulkAllocateAsset(params: Object[]): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`assetDetail/allocate/list`, params).subscribe({
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

  deallocateAsset(params: Object): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.post(`assetDetail/deallocate`, params).subscribe({
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
