import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { VerifyOtpComponent } from './components/auth/verify-otp/verify-otp.component';
import { GenerateComponent } from './components/questionnaire/generate/generate.component';
import { SubmitComponent } from './components/questionnaire/submit/submit.component';
import { AdaptiveComponent } from './components/questionnaire/adaptive/adaptive.component';
import {QuizComponent} from './components/quiz/quiz.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'verify-otp', component: VerifyOtpComponent },
  { path: 'questionnaire/generate', component: GenerateComponent },
  { path: 'questionnaire/submit', component: SubmitComponent },
  { path: 'questionnaire/adaptive', component: AdaptiveComponent },
  { path: 'questionnaire/quiz', component: QuizComponent },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
