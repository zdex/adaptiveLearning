import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {studentDTO} from  '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private apiUrl = 'http://localhost:8080/api/students'; // ✅ Adjust if needed for your API base path

  constructor(private http: HttpClient) {}

  // ✅ Fetch all students
  getAllStudents(): Observable<studentDTO[]> {
    return this.http.get<studentDTO[]>(`${this.apiUrl}`);
  }

  // ✅ Fetch single student by ID
  getStudentById(studentId: string): Observable<studentDTO> {
    return this.http.get<studentDTO>(`${this.apiUrl}/${studentId}`);
  }

  /**
   * Submits a list of student profiles to the backend, attaching the JWT for authentication.
   * The Observable return type is explicitly typed as StudentDto[].
   */
  saveStudents(students: studentDTO[]): Observable<studentDTO[]> {
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('No authentication token found. Please log in.');
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<studentDTO[]>(this.apiUrl, students, {headers});
  }
  // ✅ Delete a student
  deleteStudent(studentId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${studentId}`);
  }

  // ✅ NEW: Fetch students belonging to a parent
  getStudentsForParent(parentId: string): Observable<studentDTO[]> {
    return this.http.get<studentDTO[]>(`${this.apiUrl}/parent/${parentId}`);
  }

  setSelectedStudent(student: studentDTO) {


  }
}
