// src/app/models/question.model.ts

/**
 * Represents a single question in the questionnaire.
 */
export interface QuestionDTO {
  questionId: string;
  text: string;
  type: string;                // e.g. "MCQ", "SHORT_ANSWER", etc.
  options: string[];           // list of answer choices
  correctIndex: number | null; // correct answer (index of options)
}
