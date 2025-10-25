import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient) {}

  private headers() {
    const token = localStorage.getItem('jwt');
    return token ? { headers: new HttpHeaders({ Authorization: `Bearer ${token}` }) } : {};
    }

  register(data:any)   { return this.http.post(`${environment.apiBase}/auth/register`, data); }
  verifyOtp(data:any)  { return this.http.post(`${environment.apiBase}/auth/verify-otp`, data); }
  login(data:any)      { return this.http.post<any>(`${environment.apiBase}/auth/login`, data); }

  generateQuestionnaire(data:any) {
    return this.http.post(`${environment.apiBase}/questionnaire/generate`, data, this.headers());
  }
  submitAnswers(data:any) {
    return this.http.post(`${environment.apiBase}/questionnaire/submit`, data, this.headers());
  }
  nextAdaptive(data:any) {
    return this.http.post(`${environment.apiBase}/questionnaire/next-adaptive`, data, this.headers());
  }
}
