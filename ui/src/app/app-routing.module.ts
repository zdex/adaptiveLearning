import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { VerifyOtpComponent } from './components/auth/verify-otp/verify-otp.component';
import { GenerateComponent } from './components/questionnaire/generate/generate.component';
import { StudentManagerComponent } from './components/student-manager/student-manager-component';
import {ParentProfileComponent} from './components/parent-profile-component/parent-profile-component';


export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'verify-otp', component: VerifyOtpComponent },
  { path: 'questionnaire/generate', component: GenerateComponent },
  { path: 'students', component: StudentManagerComponent },
  { path: 'parent-profile', component: ParentProfileComponent },
  { path: '**', redirectTo: 'login' },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
