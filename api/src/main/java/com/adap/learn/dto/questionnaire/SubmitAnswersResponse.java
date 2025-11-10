package com.adap.learn.dto.questionnaire;

import lombok.Builder;

import java.util.List;

/**
 * ✅ SubmitAnswersResponse (record version)
 *
 * Represents the response returned after a student submits their answers
 * to a questionnaire. It includes scoring information, accuracy percentage,
 * and optionally a breakdown of each question's correctness.
 */
@Builder
public record SubmitAnswersResponse(
        String questionnaireId,
        String studentId,
        int correctCount,
        int totalQuestions,
        double percentage,
        String nextDifficulty,
        String message,
        List<AnswerResult> answers
) {
    /**
     * Nested record representing per-question evaluation result.
     */
    public record AnswerResult(
            String questionId,
            String text,
            String selectedAnswer,
            String correctAnswer,
            boolean correct
    ) { }
}
