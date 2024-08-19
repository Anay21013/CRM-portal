import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LoaderService } from 'projects/vhub/src/app/services/loader.service';
import { AuthService } from 'projects/vhub/src/app/services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from 'projects/vhub/src/app/services/employee.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from 'projects/vhub/src/environments/environment';
import { LovService } from '../../services/lov.service';

@Component({
  selector: 'vhub-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  appName = environment.app_name;
  form: FormGroup;
  redirectUrl: string = 'app/profile';
  durationInSeconds: 5;
  constructor(
    private readonly  fb: FormBuilder,
    private readonly  loaderService: LoaderService,
    private readonly  authService: AuthService,
    private readonly  router: Router,
    private readonly  route: ActivatedRoute,
    private readonly  employeeService: EmployeeService,
    private readonly  snackBar: MatSnackBar,
    private readonly  lovService: LovService
  ) { }

  ngOnInit(): void {
    this.createform();
    this.route.queryParams.subscribe((res: any) => {
      if (res.redirect_url) {
        this.redirectUrl = (res.redirect_url).split('_').join('/');
      }
    });
  }

  onSubmit() {
    this.snackBar.dismiss();
    this.loaderService.startLoading();
    const body = {
      username: this.form.controls['username'].value,
      password: this.form.controls['password'].value
    };
    this.authService.login(body).then((res) => {
      if (res) {
        res.time = new Date();
        res.token = this.authService.encrypt(res.token);
        localStorage.setItem('authentication', JSON.stringify(res));
        this.lovService.getLovList();
        this.employeeService.getEmployeeDetailsByUserId(res?.id, true).then(() => {
          if (res?.verified) {
            this.router.navigate([this.redirectUrl]);
          }
          else {
            this.router.navigate(['app/auth/confirm-password']);
          }
          this.loaderService.stopLoading();
        });
      }
    }).catch(error => {
      this.loaderService.stopLoading();
      this.snackBar.open(error?.message, "OK");
    });
  }

  createform(): void {
    this.form = this.fb.group({
      username: [null],
      password: [null],
      captcha: [null]
    });
  }

  prevent(evt: any) {
    evt.preventDefault();
  }
}
