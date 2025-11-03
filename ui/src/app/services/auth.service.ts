import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth'; // backend base URL

  constructor(private http: HttpClient) {}

  /**
   * Register a new user
   */
  register(user: {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user, {
      withCredentials: true
    });
  }

  /**
   * Login user and store JWT
   */
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials, {
      withCredentials: true
    }).pipe(
      tap((response: any) => {
        if (response?.token) {
          localStorage.setItem('token', response.token);
        }
      })
    );
  }

  /**
   * Logout user
   */
  logout(): void {
    localStorage.removeItem('token');
  }

  /**
   * Get JWT token
   */
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  /**
   * Check if user is logged in
   */
  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  /**
   * Fetch current logged-in user (optional)
   */
  getCurrentUser(): Observable<any> {
    const token = this.getToken();
    if (!token) {
      throw new Error('No token found');
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.get(`${this.apiUrl}/me`, { headers });
  }
}
