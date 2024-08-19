import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { matchValueValidator, pattern, required } from 'projects/vhub/src/app/common-utils/form-validation/utils/validations';
import { LoaderService } from '../../services/loader.service';
import { passwordValidation } from 'projects/vhub/src/app/common-utils/form-validation/validation';
@Component({
  selector: 'vhub-signup',
  templateUrl: './signup.component.html'
})
export class SignupComponent implements OnInit {

  form: FormGroup;
  loginErrorMessage: string;
  isLogin = true;

  constructor(
    private readonly  fb: FormBuilder,
    private readonly loaderService: LoaderService
  ) {
  }

  ngOnInit(): void {
    this.createform();
  }
  onSwitchMode() {
    this.isLogin = !this.isLogin;
  }
  onSubmit(): void {
    this.loaderService.startLoading();
  }
  createform(): void {
    this.form = this.fb.group({
      email: [null, [required('*Email is required')]],
      password: [null, [required('*Password is required'), pattern(passwordValidation, `Password is weak`)]],
      confirmPassword: [null, [required('*Confirm Password is required')]]
    },
      ({
        validator: matchValueValidator('password', 'confirmPassword', "Password doesn't match")
      } as any)
    );

  }
}
