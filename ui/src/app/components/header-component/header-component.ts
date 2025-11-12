import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Observable } from 'rxjs';

interface Student {
  studentId: number;
  fullName: string;
}

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header-component.html',
  styleUrls: ['./header-component.css'],
})
export class HeaderComponent implements OnInit {
  // Observable to hold the login status
  isLoggedIn$!: Observable<boolean>;

  // Mock student data to populate the dropdown
  students: Student[] = [
    { studentId: 1, fullName: 'Alice Johnson' },
    { studentId: 2, fullName: 'Bob Smith' },
    { studentId: 3, fullName: 'Charlie Brown' },
  ];

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    // Get the reactive status from the AuthService
    this.isLoggedIn$ = this.authService.isLoggedIn$;
  }

  /**
   * Handles user logout, clears token, and redirects.
   */
  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']); // Redirect to login page after logout
  }

  /**
   * Example navigation function for selecting a student
   * @param studentId The ID of the selected student
   */
  onSelectStudent(studentId: number): void {
    console.log('Selected student ID:', studentId);
    // In a real application, you would navigate to a dashboard for this student,
    // or set the student as the active context in a service.
    this.router.navigate(['/students', studentId, 'dashboard']);
  }
}
