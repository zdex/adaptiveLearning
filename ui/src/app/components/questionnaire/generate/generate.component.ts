import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import {QuestionnaireService} from '../../../services/questionnaire.service';
import {GenerateQuestionnaireResponse, SubmitAnswersRequest} from '../../../models/questionnaire.model';
import {QuestionDTO} from '../../../models/question.model';
//import { QuestionDTO, GenerateQuestionnaireResponse } from '../../models/questionnaire.model';


type Question = {
  questionId: string;
  text: string;
  type: string;                // "MCQ" expected
  options: string[];
  correctIndex: number | null; // from backend
};

type GenerateResponse = {
  prompt: string;
  rawResponse: string;
  questions: Question[];
};

@Component({
  selector: 'app-generate-questionnaire',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './generate.component.html',
  styleUrls: ['./generate.component.css']
})
export class GenerateComponent {
  // form fields
  subject = '';
  grade = '';
  style: 'MCQ' | 'SHORT_ANSWER' = 'MCQ';

  // state
  loading = false;
  message = '';

  // selection state: questionId -> selected option index (or null)
  answers: Record<string, number | null> = {};

  // evaluation state after submit: questionId -> true/false/null (unanswered)
  results: Record<string, boolean | null> = {};
  generated?: GenerateQuestionnaireResponse;
  submitted = false;

  generatingNew = false;
  nextDifficulty: string = 'easy';

  constructor(private questionnaireService: QuestionnaireService) {}

  generate() {
    this.loading = true;
    this.submitted = false;
    this.message = '';
    this.generated = undefined;
    this.answers = {};
    this.results = {};


    const payload = { subject: this.subject, grade: this.grade, style: this.style, difficultyLevel: this.nextDifficulty };

    this.questionnaireService.generateQuestionnaire(payload).subscribe({
      next: (res) => {
        // filter to MCQ only (UI below renders MCQ)
        const onlyMcq = (res.questions || []).filter((q: QuestionDTO) => (q.type || 'MCQ') === 'MCQ');
        this.generated = { ...res, questions: onlyMcq };

        // init answers as null
        onlyMcq.forEach((q: QuestionDTO) => (this.answers[q.questionId] = null));
      },
      error: (err) => {
        console.error(err);
        this.message = err?.error?.message || 'Failed to generate questionnaire.';
      },
      complete: () => (this.loading = false)
    });
  }

  // capture a selection
  choose(questionId: string, optionIndex: number) {
    this.answers[questionId] = optionIndex;
  }

  // submit locally: compare against correctIndex and render results
  submit() {
    if (!this.generated?.questions?.length) return;

    this.results = {};
    this.generated.questions.forEach((q: QuestionDTO) => {
      const chosen = this.answers[q.questionId];
      if (chosen === null || chosen === undefined) {
        this.results[q.questionId] = null; // unanswered
      } else if (typeof q.correctIndex === 'number') {
        this.results[q.questionId] = chosen === q.correctIndex;
      } else {
        // if backend omitted correctIndex, treat as unanswered/unknown
        this.results[q.questionId] = null;
      }
    });
    const answersPayload: SubmitAnswersRequest = {
      studentId: 'S1',
      questionnaireId: 'QSet1',
      answers: Object.entries(this.answers).map(([qid, idx]) => ({
        questionId: qid,
        selectedIndex: idx
      }))
    };
    this.questionnaireService.submitAnswers(answersPayload).subscribe({
      next: (res) => {
        this.submitted = true;
        this.nextDifficulty = res.nextDifficulty;
        console.log('Submit Result:', res);
      },
      error: (err) => console.error('Submit failed:', err)
    });
    this.submitted = true;
  }
  resetAnswers() {
    if (!this.generated?.questions) return;
    this.generated.questions.forEach((q: QuestionDTO) => (this.answers[q.questionId] = null));
    this.results = {};
    this.submitted = false;
  }

  // helpers for template
  answeredCount(): number {
    return Object.values(this.answers).filter(v => v !== null && v !== undefined).length;
  }

  correctCount(): number {
    return Object.values(this.results).filter(v => v === true).length;
  }
}
