// src/app/models/questionnaire.model.ts
import { QuestionDTO } from './question.model';

/**
 * Represents the OpenAI-generated questionnaire response.
 */
export interface GenerateQuestionnaireResponse {
  prompt: string;
  rawResponse: string;
  questions: QuestionDTO[];
}

/**
 * Represents a user's submitted answer for one question.
 */
export interface SubmittedAnswer {
  questionId: string;
  selectedIndex: number | null; // null if user skipped
}

/**
 * Represents the full answer submission payload.
 */
export interface SubmitAnswersRequest {
  studentId: string;
  questionnaireId?: string; // optional, if backend assigns one
  answers: SubmittedAnswer[];
}
