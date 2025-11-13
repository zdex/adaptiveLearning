import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

import { AppRoutingModule, routes } from './app-routing.module';

// Root Component
import { AppComponent } from './app.component';

// Components (standalone components are only imported in 'imports', module-based in 'declarations')
import { LoginComponent } from './components/auth/login/login.component'; // Standalone
import { RegisterComponent } from './components/auth/register/register.component'; // Standalone
import { VerifyOtpComponent } from './components/auth/verify-otp/verify-otp.component'; // Module-based
import { HeaderComponent } from './components/header-component/header-component'; // Standalone

// Services
import { ApiService } from './services/api.service';
import { AuthService } from './services/auth.service';
import { QuestionnaireService } from './services/questionnaire.service';
import {ParentProfileComponent} from './components/parent-profile-component/parent-profile-component';


@NgModule({
  // DECLARATIONS: Only module-based components and the root component
  declarations: [
    AppComponent, // Re-added the root component here
    VerifyOtpComponent,
    HeaderComponent,
    ParentProfileComponent
  ],
  // IMPORTS: All modules and standalone components
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CommonModule,
    RouterOutlet, // Ensures the <router-outlet> in app.component.html works
    // Standalone Components (imported here for use in templates/routing)
    // Imported here so it can be used in app.component.html
    RegisterComponent,
    LoginComponent,
    // CRITICAL: This provides the ActivatedRoute and all other routing services
    RouterModule.forRoot(routes || [])
  ],
  providers: [ApiService, AuthService, QuestionnaireService],
  // BOOTSTRAP: The root component for the module
  bootstrap: [AppComponent]
})
export class AppModule { }
