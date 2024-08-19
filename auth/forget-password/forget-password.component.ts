import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoaderService } from 'projects/vhub/src/app/services/loader.service';
import { AuthService } from 'projects/vhub/src/app/services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'vhub-forget-password',
  templateUrl: './forget-password.component.html'
})
export class ForgetPasswordComponent implements OnInit {
  form: FormGroup;
  constructor(
    private readonly  fb: FormBuilder,
    private readonly  router: Router,
    private readonly  toastrService: ToastrService,
    private readonly  loaderService: LoaderService,
    private readonly  authService: AuthService,
    private readonly  snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.createUserForm();
  }
  onSubmit() {
    this.snackBar.dismiss();
    this.loaderService.startLoading();
    this.authService.forgotPassword(this.form.value.email).then(res => {
      if (res) {
        this.loaderService.stopLoading();
        this.router.navigate([`app/auth/reset-password/${this.form.value.email}`]);
        this.toastrService.success("verification code sent");
      }
    }).catch(error => {
      this.loaderService.stopLoading();
      this.snackBar.open(error?.message, "OK");
    });
  }
  createUserForm(): void {
    this.form = this.fb.group({
      email: [null],
      captcha: [null]
    });
  }
}
