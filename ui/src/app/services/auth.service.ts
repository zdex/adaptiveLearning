import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import {parentDTO} from '../models/user.model';

// Define expected response structure for login
interface AuthResponse {
  message: string;
  token: string;
}

// Define structure for login credentials (assuming you have a DTO on the backend)
interface LoginCredentials {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Reactive state management for login status. Initial check is based on token presence.
  private loggedIn = new BehaviorSubject<boolean>(this.checkToken());
  isLoggedIn$: Observable<boolean> = this.loggedIn.asObservable();

  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  /** Checks if a JWT token is present in local storage. */
  private checkToken(): boolean {
    return !!localStorage.getItem('token');
  }

  /**
   * Stores the token and updates the login status reactively.
   * @param token The JWT token received from the backend.
   */
  setToken(token: string): void {
    localStorage.setItem('token', token);
    this.loggedIn.next(true); // Notify all subscribers (like AppComponent)
  }

  /** Clears the token and updates the login status. */
  logout(): void {
    localStorage.removeItem('token');
    this.loggedIn.next(false); // Notify all subscribers
  }

  // Placeholder methods matching usage in Login/Register components

  login(credentials: LoginCredentials): Observable<AuthResponse> {
    // This should call your Spring Boot login endpoint
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials);
  }

  register(user: any): Observable<any> {
    // This should call your Spring Boot register endpoint
    return this.http.post<any>(`${this.apiUrl}/register`, user);
  }

  getUser(userId: any): Observable<parentDTO> {
    // This should call your Spring Boot register endpoint
    return this.http.post<parentDTO>(`${this.apiUrl}/getUserDetails`, userId);
  }
}
