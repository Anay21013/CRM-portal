import { Injectable } from "@angular/core";
import { ApiService } from "./api.service";

@Injectable({
    providedIn: 'root'
})
export class RoleService {

    constructor(private readonly apiService: ApiService) { }

    get permissions(): any[] {
        return (JSON.parse(localStorage.getItem('authentication'))?.role?.permissions?.concat([
            {
                name: "default",
                description: "Default"
            }
        ]));
    }

    getRoles(): Promise<any> {
        return new Promise((resolve, reject) => {
            this.apiService.get('role').subscribe({
                next: (res) => {
                    resolve(res);
                },
                error: (error) => {
                    reject(error?.error);
                }
            });
        });
    }

    getRolesById(id: number): Promise<any> {
        return new Promise((resolve, reject) => {
            this.apiService.get(`role/${id}`).subscribe({
                next: (res) => {
                    resolve(res);
                },
                error: (error) => {
                    reject(error?.error);
                }
            });
        });
    }

    addRole(params: any): Promise<any> {
        return new Promise((resolve, reject) => {
            this.apiService.post('role', params).subscribe({
                next: (sucess) => {
                    resolve(sucess);
                },
                error: (error) => {
                    reject(error?.error);
                }
            });
        });
    }

    addRolePermission(roleId: number, params: any): Promise<any> {
        return new Promise((resolve, reject) => {
            this.apiService.post(`role/${roleId}/permission?permissionList=${params}`).subscribe({
                next: (sucess) => {
                    resolve(sucess);
                },
                error: (error) => {
                    reject(error?.error);
                }
            });
        });
    }

    deleteRolePermission(roleId: number, permissionId: any): Promise<any> {
        return new Promise((resolve, reject) => {
            this.apiService.delete(`role/${roleId}/permission/${permissionId}`).subscribe({
                next: (sucess) => {
                    resolve(sucess);
                },
                error: (error) => {
                    reject(error?.error);
                }
            });
        });
    }

    updateRole(params: any): Promise<any> {
        return new Promise((resolve, reject) => {
            this.apiService.put(`role`, params).subscribe({
                next: (sucess) => {
                    resolve(sucess);
                },
                error: (error) => {
                    reject(error?.error);
                }
            });
        });
    }

    deleteRole(id: number): Promise<any> {
        return new Promise((resolve, reject) => {
            this.apiService.delete(`role/${id}`).subscribe({
                next: (sucess) => {
                    resolve(sucess);
                },
                error: (error) => {
                    reject(error?.error);
                }
            });
        });
    }

    getAllPermissions(): Promise<any> {
        return new Promise((resolve, reject) => {
            this.apiService.get(`role/permissions`).subscribe({
                next: (res) => {
                    resolve(res);
                },
                error: (error) => {
                    reject(error?.error);
                }
            });
        });
    }

    getRolePermissions(roleId: number): Promise<any> {
        return new Promise((resolve, reject) => {
            this.apiService.get(`all/permissions/${roleId}`).subscribe({
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
