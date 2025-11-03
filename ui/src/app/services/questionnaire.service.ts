import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class QuestionnaireService {
  private baseUrl = 'http://localhost:8080/api/service/questionnaire';

  constructor(private http: HttpClient) {}

  private getHeaders() {
    const token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
    };
  }

  generateQuestionnaire(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/generate`, data, this.getHeaders());
  }

  submitAnswers(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/submit`, data, this.getHeaders());
  }

  nextAdaptive(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/next-adaptive`, data, this.getHeaders());
  }
}
