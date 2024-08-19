import { Routes } from '@angular/router';
import { AuthComponent } from './auth.component';
import { LoginComponent } from 'projects/vhub/src/app/auth/login/login.component';
import { SignupComponent } from 'projects/vhub/src/app/auth/signup/signup.component';
import { authRestricGuard } from 'projects/vhub/src/app/common-utils/guards/auth-restric.guard';
import { unverifiedAuthGuard } from 'projects/vhub/src/app/common-utils/guards/auth-unverified.guard';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { ConfirmPasswordComponent } from './confirm-password/confirm-password.component';

export const authRoutes: Routes = [
  {
    path: 'auth',
    component: AuthComponent,
    data: {
      breadcrumb: { skip: true }
    },
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
      },
      {
        path: 'login',
        component: LoginComponent,
        canActivate: [authRestricGuard],
        data: {
          title: 'Login',
          breadcrumb: { skip: true }
        }
      },
      {
        path: 'signup',
        component: SignupComponent,
        canActivate: [authRestricGuard],
        data: {
          title: 'Signup',
          breadcrumb: { skip: true }
        }
      },
      {
        path: 'reset-password/:email',
        component: ResetPasswordComponent,
        canActivate: [authRestricGuard],
        data: {
          title: 'Reset Password',
          breadcrumb: { skip: true }
        }
      },
      {
        path: 'confirm-password',
        component: ConfirmPasswordComponent,
        canActivate: [unverifiedAuthGuard],
        data: {
          title: 'Confirm Password',
          breadcrumb: { skip: true }
        }

      },
      {
        path: 'forgot-password',
        component: ForgetPasswordComponent,
        canActivate: [authRestricGuard],
        data: {
          title: 'Forgot Password',
          breadcrumb: { skip: true }
        }
      },
      {
        path: '**',
        redirectTo: 'login',
        pathMatch: 'full'
      }
    ]
  }
];
