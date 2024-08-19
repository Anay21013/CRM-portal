import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { matchValueValidator, pattern, required } from 'projects/vhub/src/app/common-utils/form-validation/utils/validations';
import { LoaderService } from 'projects/vhub/src/app/services/loader.service';
import { AuthService } from 'projects/vhub/src/app/services/auth.service';
import { Router } from '@angular/router';
import { passwordValidation } from 'projects/vhub/src/app/common-utils/form-validation/validation';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'vhub-confirm-password',
  templateUrl: './confirm-password.component.html'
})
export class ConfirmPasswordComponent implements OnInit {

  form: FormGroup;

  constructor(
    private readonly  fb: FormBuilder,
    private readonly  loaderService: LoaderService,
    private readonly  toastrService: ToastrService,
    private readonly  authService: AuthService,
    private readonly  router: Router,
    private readonly  snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.createform();
  }
  createform(): void {
    this.form = this.fb.group({
      password: [null, [required('*Password is required'), pattern(passwordValidation, `Password is weak`)]],
      confirmPassword: [null, [required('*Confirm Password is required')]]
    },
      ({
        validator: matchValueValidator('password', 'confirmPassword', "Password doesn't match")
      } as any)
    );
  }
  onSubmit() {
    this.loaderService.startLoading();
    const body = {
      userId: this.authService.authentication?.id,
      token: this.authService.authentication?.token,
      password: this.form.controls["password"].value
    };
    this.authService.updatePassword(body).then(() => {
      const authentication = JSON.parse(localStorage.getItem("authentication"));
      authentication.verified = true;
      localStorage.setItem("authentication", JSON.stringify(authentication));
      this.router.navigate(['app/profile']);
      this.loaderService.stopLoading();
      this.toastrService.success('Password reset successful', "Welcome");
    }).catch(error => {
      this.loaderService.stopLoading();
      this.snackBar.open(error?.error, "OK");
    });
  }
}
