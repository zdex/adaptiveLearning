import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';


// Components
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { VerifyOtpComponent } from './components/auth/verify-otp/verify-otp.component';
import { GenerateComponent } from './components/questionnaire/generate/generate.component';
import { SubmitComponent } from './components/questionnaire/submit/submit.component';
import { AdaptiveComponent } from './components/questionnaire/adaptive/adaptive.component';

// Services
import { ApiService } from './services/api.service';
import { AuthService } from './services/auth.service';
import { QuestionnaireService } from './services/questionnaire.service';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    VerifyOtpComponent,
    GenerateComponent,
    SubmitComponent,
    AdaptiveComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [ApiService, AuthService, QuestionnaireService],
  bootstrap: [AppComponent]
})
export class AppModule { }
