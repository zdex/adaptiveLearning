import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { StudentService} from '../../services/StudentService';
import {parentDTO, studentDTO} from '../../models/user.model';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-parent-profile',
  standalone: false,
  templateUrl: './parent-profile-component.html',
  styleUrls: ['./parent-profile-component.css']
})
export class ParentProfileComponent implements OnInit {

  parent: any;
  students: studentDTO[] = [];
  loading = true;

  constructor(
    private authService: AuthService,
    private studentService: StudentService
  ) {}

  ngOnInit(): void {
    const user: Observable<parentDTO> = this.authService.getUser("parentId");
    this.parent = user;

  /*  if (user.id) {
      this.studentService.getStudentsForParent(user.id).subscribe({
        next: (data) => {
          this.students = data;
          this.loading = false;
        },
        error: (err) => {
          console.error('Failed to load students:', err);
          this.loading = false;
        }
      });
    } else {
      this.loading = false;
    }*/
  }
}
