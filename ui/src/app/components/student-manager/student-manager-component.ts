import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
// IMPORTANT: Import both StudentService and StudentDto
import { StudentService, StudentDto } from '../../services/StudentService';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-manager',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './student-manager-component.html',
  styleUrls: ['./student-manager-component.css']
})
export class StudentManagerComponent {

  // List of students to be submitted
  students: StudentDto[] = [];

  // Model for the new student input form
  newStudent: StudentDto = {
    firstName: '',
    lastName: '',
    age: null,
    grade: null,
    dateOfBirth: ''
  };

  message: string = '';
  loading: boolean = false;

  // List of valid grades for a dropdown (e.g., K-12)
  validGrades: number[] = Array.from({ length: 13 }, (_, i) => i); // 0 (Kindergarten) to 12

  constructor(
    private studentService: StudentService,
    private router: Router
  ) {
    // Initialize the first student slot
    this.addStudent();
  }

  /**
   * Adds a new, empty student DTO to the list.
   * It uses a deep copy of the empty template to prevent binding issues.
   */
  addStudent() {
    this.students.push({ ...this.newStudent });
  }

  /**
   * Removes a student from the list by index.
   * @param index The index of the student to remove.
   */
  removeStudent(index: number) {
    this.students.splice(index, 1);
  }

  /**
   * Checks if a single student entry is valid.
   */
  isStudentValid(student: StudentDto): boolean {
    return !!student.firstName &&
      !!student.lastName &&
      (student.grade !== null && student.grade !== undefined) && // Check for grade explicitly
      !!student.dateOfBirth;
  }

  /**
   * Checks if the entire list of students is valid for submission.
   */
  isFormValid(): boolean {
    if (this.students.length === 0) return false;
    // Check every student in the list for validity
    return this.students.every(s => this.isStudentValid(s));
  }


  /**
   * Submits the list of valid students to the Spring Boot backend.
   */
  onSubmitStudents() {
    if (!this.isFormValid()) {
      this.message = 'Please fill out all required fields for every student.';
      return;
    }

    this.loading = true;
    this.message = 'Submitting profiles...';

    const validStudents = this.students.filter(s => this.isStudentValid(s));

    // @ts-ignore
    this.studentService.saveStudents(validStudents).subscribe({
      // FIX APPLIED HERE: Explicitly type 'savedStudents' as StudentDto[]
      next: (savedStudents: StudentDto[]) => {
        this.message = `${savedStudents.length} student profile(s) successfully created!`;
        this.loading = false;
        // Navigate to the next step
        setTimeout(() => {
          this.router.navigate(['/questionnaire/generate']);
        }, 1500);
      },
      error: (err) => {
        console.error('Submission failed:', err);
        this.message = `Error saving student profiles: ${err || 'Check the console for details.'}`;
        this.loading = false;
      }
    });
  }

  // Helper for tracking in the ngFor loop
  trackByFn(index: number, student: StudentDto): any {
    return index;
  }
}
