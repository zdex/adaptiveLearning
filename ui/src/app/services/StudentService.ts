import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

// Define a Student interface for type safety
export interface StudentDTO {
  id?: string;
  firstName: string;
  lastName: string;
  email?: string;
  gradeLevel?: string;
  parentId?: string;
  age?: number;
  grade?: string;
  dateOfBirth?: string;
}

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private apiUrl = 'http://localhost:8080/api/students'; // ✅ Adjust if needed for your API base path

  constructor(private http: HttpClient) {}

  // ✅ Fetch all students
  getAllStudents(): Observable<StudentDTO[]> {
    return this.http.get<StudentDTO[]>(`${this.apiUrl}`);
  }

  // ✅ Fetch single student by ID
  getStudentById(studentId: string): Observable<StudentDTO> {
    return this.http.get<StudentDTO>(`${this.apiUrl}/${studentId}`);
  }

  /**
   * Submits a list of student profiles to the backend, attaching the JWT for authentication.
   * The Observable return type is explicitly typed as StudentDto[].
   */
  saveStudents(students: StudentDTO[]): Observable<StudentDTO[]> {
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('No authentication token found. Please log in.');
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<StudentDTO[]>(this.apiUrl, students, {headers});
  }
  // ✅ Delete a student
  deleteStudent(studentId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${studentId}`);
  }

  // ✅ NEW: Fetch students belonging to a parent
  getStudentsForParent(parentId: string): Observable<StudentDTO[]> {
    return this.http.get<StudentDTO[]>(`${this.apiUrl}/parent/${parentId}`);
  }

  setSelectedStudent(student: StudentDTO) {


  }
}
