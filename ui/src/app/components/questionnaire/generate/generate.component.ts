import { Component } from '@angular/core';
import { QuestionnaireService } from '../../../services/questionnaire.service';

@Component({
  selector: 'app-generate-questionnaire',
  templateUrl: './generate.component.html',
  styleUrls: ['./generate.component.css'],
  standalone: false
})
export class GenerateComponent {
  subject = 'Mathematics';
  grade = 'Grade 7';
  style = 'MCQ';
  generated: any;
  loading = false;
  message = '';

  constructor(private questionnaireService: QuestionnaireService) {}

  generate() {
    this.loading = true;
    this.message = 'Generating questionnaire...';
    this.generated = null;

    const payload = { subject: this.subject, grade: this.grade, style: this.style };

    this.questionnaireService.generateQuestionnaire(payload).subscribe({
      next: (res) => {
        this.loading = false;
        this.generated = res;
        this.message = '✅ Questionnaire generated successfully!';
      },
      error: (err) => {
        this.loading = false;
        this.message = '❌ Failed to generate questionnaire: ' + err.message;
      }
    });
  }
}
