import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { matchValueValidator, pattern, required } from 'projects/vhub/src/app/common-utils/form-validation/utils/validations';
import { emailValidation, passwordValidation } from 'projects/vhub/src/app/common-utils/form-validation/validation';
import { AuthService } from 'projects/vhub/src/app/services/auth.service';
import { LoaderService } from 'projects/vhub/src/app/services/loader.service';

@Component({
  selector: 'vhub-reset-password',
  templateUrl: './reset-password.component.html'
})
export class ResetPasswordComponent implements OnInit {

  form: FormGroup;
  email: string;

  constructor(
    private readonly  fb: FormBuilder,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly toastrService: ToastrService,
    private readonly loaderService: LoaderService,
    private readonly authService: AuthService,
    private readonly snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: any) => {
      if (params?.email?.match(new RegExp(emailValidation))) {
        this.email = params?.email;
      } else {
        this.toastrService.warning('Please give a valid email address.');
        this.router.navigate(['app/auth/forgot-password']);
      }
    });
    this.createform();
  }
  createform(): void {
    this.form = this.fb.group({
      token: [null, [required('*code is required')]],
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
      email: this.email,
      token: this.form.value.token,
      password: this.form.value.password
    };
    this.authService.resetPassword(body).then(() => {
      this.loaderService.stopLoading();
      this.toastrService.success("Your Password is now changed", "Password Reset");
      this.router.navigate(['app/auth/login']);
    }).catch(error => {
      this.loaderService.stopLoading();
      this.snackBar.open(error?.error, "OK");
    });
  }

  resendEmail() {
    this.toastrService.success("verification code resent");
  }
}
