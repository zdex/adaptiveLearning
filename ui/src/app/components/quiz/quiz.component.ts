import { Component, Input } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html'
})
export class QuizComponent {
  @Input() questionnaireId?: string;
  studentId='student-1';
  answers:any[]=[];
  result:any; message='';

  // in real app, fetch questions by id; here we keep it simple
  constructor(private api: ApiService) {}

  submit() {
    const payload = { questionnaireId: this.questionnaireId, studentId: this.studentId, answers: this.answers };
    this.api.submitAnswers(payload).subscribe({
      next: res => this.result = res,
      error: err => this.message = 'Submit failed'
    });
  }
}
