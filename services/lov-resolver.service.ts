import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { LoaderService } from './loader.service';
import { LovService } from './lov.service';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class LovResolverService  {

  constructor(
    private readonly loaderService: LoaderService,
    private readonly lovService: LovService,
    private readonly toastrService: ToastrService,
    private readonly authService: AuthService
  ) { }

  resolve(): Observable<any> {
    return new Observable((observer) => {
      const employeeId = this.authService.authentication?.['id'];
      if (employeeId && !this.lovService.lov) {
        this.loaderService.startLoading();
        this.lovService.getLovList().then((res) => {
          observer.next(res);
          observer.complete();
          this.loaderService.stopLoading();
        }).catch(error => {
          this.loaderService.stopLoading();
          this.toastrService.error(error?.error);
        });
      } else {
        observer.next(this.lovService.lov);
        observer.complete();
      }
    });
  }
}
