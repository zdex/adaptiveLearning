import { Component } from '@angular/core';
import { QuestionnaireService } from '../../../services/questionnaire.service';
import { CommonModule, JsonPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-adaptive-questionnaire',
  templateUrl: './adaptive.component.html',
  styleUrls: ['./adaptive.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, JsonPipe]
})
export class AdaptiveComponent {
  studentId = 'student-001';
  subject = 'Mathematics';
  difficulty = 'MEDIUM';
  adaptiveResponse: any;
  message = '';

  constructor(private questionnaireService: QuestionnaireService) {}

  getNextAdaptive() {
    const payload = {
      studentId: this.studentId,
      subject: this.subject,
      targetDifficulty: this.difficulty
    };

    this.questionnaireService.nextAdaptive(payload).subscribe({
      next: (res) => {
        this.adaptiveResponse = res;
        this.message = '✅ Adaptive questionnaire generated!';
      },
      error: (err) => {
        this.message = '❌ Failed: ' + err.message;
      }
    });
  }
}
