import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../services/auth.service'; // Assuming AuthService path

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header-component.html',
  styleUrls: ['./header-component.css'],
})
export class HeaderComponent implements OnInit {
  isLoggedIn$!: Observable<boolean>;
  isDropdownOpen = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    // Get the reactive login status from the AuthService
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
   * Toggles the dropdown menu visibility.
   * Prevents the click event from bubbling up to potentially close the dropdown immediately.
   */
  toggleDropdown() {
    //event.stopPropagation();
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  /**
   * Closes the dropdown (used for mouseleave or item selection).
   */
  closeDropdown() {
    this.isDropdownOpen = false;
  }
}
