import { Component } from '@angular/core';
import { QuestionnaireService } from '../../../services/questionnaire.service';

@Component({
  selector: 'app-submit-questionnaire',
  templateUrl: './submit.component.html',
  styleUrls: ['./submit.component.css'],
  standalone: false
})
export class SubmitComponent {
  questionnaireId = '';
  studentId = 'student-001';
  questions: any[] = [];
  answers: any = {};
  result: any;
  message = '';

  constructor(private questionnaireService: QuestionnaireService) {}

  // Fetch questions by questionnaireId (for demo, you may already have them)
  loadMockQuestions() {
    this.questions = [
      { questionId: 'q1', text: 'What is 12 × 8?', options: ['80', '88', '96', '102'] },
      { questionId: 'q2', text: 'Simplify 4(2x + 3)', options: ['8x + 3', '8x + 12', '4x + 6', '8x + 6'] }
    ];
  }

  submit() {
    const payload = {
      questionnaireId: this.questionnaireId,
      studentId: this.studentId,
      answers: Object.keys(this.answers).map(qId => ({
        questionId: qId,
        selectedOptionId: this.answers[qId],
        freeText: null
      }))
    };

    this.questionnaireService.submitAnswers(payload).subscribe({
      next: (res) => {
        this.result = res;
        this.message = '✅ Submitted successfully!';
      },
      error: (err) => {
        this.message = '❌ Submission failed: ' + err.message;
      }
    });
  }
}
