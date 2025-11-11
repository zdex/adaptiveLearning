import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Defines the structure for student data transferred between the frontend and backend.
 */
export interface StudentDto {
  firstName: string;
  lastName: string;
  age: number | null;
  grade: number | null;
  dateOfBirth: string; // ISO date string (YYYY-MM-DD)
}

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiUrl = 'http://localhost:8080/api/students'; // Adjust port if necessary

  constructor(private http: HttpClient) { }

  /**
   * Submits a list of student profiles to the backend, attaching the JWT for authentication.
   * The Observable return type is explicitly typed as StudentDto[].
   */
  saveStudents(students: StudentDto[]): Observable<StudentDto[]> {
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('No authentication token found. Please log in.');
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<StudentDto[]>(this.apiUrl, students, { headers });
  }
}
