import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpInterceptor} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';



// Components
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { VerifyOtpComponent } from './components/auth/verify-otp/verify-otp.component';

// Services
import { ApiService } from './services/api.service';
import { AuthService } from './services/auth.service';
import { QuestionnaireService } from './services/questionnaire.service';
import { AppComponent } from './app.component';
// (optional) if you have routes in a separate file
import { routes } from './app-routing.module';

import { RouterModule } from '@angular/router';
@NgModule({
  declarations: [
    VerifyOtpComponent,
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RegisterComponent,
    LoginComponent,
    RouterModule.forRoot(routes || [])

  ],
  providers: [ApiService, AuthService, QuestionnaireService],
  bootstrap: [AppComponent]
})
export class AppModule { }
