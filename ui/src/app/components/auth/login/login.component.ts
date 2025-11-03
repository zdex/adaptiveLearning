import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  message = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    if (!this.email || !this.password) {
      this.message = 'Please enter both email and password.';
      return;
    }

    this.loading = true;
    this.authService.login({ email: this.email, password: this.password }).subscribe({
      next: (res) => {
        console.log('Login success:', res);
        this.message = 'Login successful! Redirecting...';

        // If backend returns JWT, store it
        if (res?.token) {
          localStorage.setItem('token', res.token);
        }

        setTimeout(() => this.router.navigate(['/questionnaire/generate']), 1500);
      },
      error: (err) => {
        console.error('Login failed:', err);
        this.message = err.error?.message || 'Invalid credentials, please try again.';
        this.loading = false;
      },
      complete: () => (this.loading = false)
    });
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
