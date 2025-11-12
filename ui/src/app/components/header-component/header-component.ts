import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import {StudentDTO, StudentService} from '../../services/StudentService';

import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header-component.html',
  styleUrls: ['../../app.component.css'] // Reusing main app CSS for header styles
})
export class HeaderComponent implements OnInit, OnDestroy {
  isLoggedIn$!: Observable<boolean>;
  students: StudentDTO[] = [];
  selectedStudent: StudentDTO | null = null;
  isDropdownOpen: boolean = false;
  private authSubscription!: Subscription;

  constructor(
    private authService: AuthService,
    private studentService: StudentService,
    private router: Router
  ) {}

  ngOnInit() {
    this.isLoggedIn$ = this.authService.isLoggedIn$;

    // Subscribe to login status changes to automatically fetch students
    this.authSubscription = this.isLoggedIn$.subscribe(isLoggedIn => {
      if (isLoggedIn) {
        this.fetchStudents();
      } else {
        // Clear students list if user logs out
        this.students = [];
        this.selectedStudent = null;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  fetchStudents(): void {
    this.studentService.getStudentsForParent("parentId").subscribe({
      next: (data) => {
        this.students = data;
        if (this.students.length > 0) {
          // Auto-select the first student if none is selected
          this.selectStudent(this.students[0]);
        }
      },
      error: (err) => {
        console.error('Failed to fetch students:', err);
        // Handle error (e.g., token expired, or user not authorized as a parent)
      }
    });
  }

  selectStudent(student: StudentDTO): void {
    this.selectedStudent = student;
    this.studentService.setSelectedStudent(student);
    this.isDropdownOpen = false; // Close dropdown after selection
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  /**
   * Handles user logout, clears token, and redirects.
   */
  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']); // Redirect to login page after logout
  }
}
