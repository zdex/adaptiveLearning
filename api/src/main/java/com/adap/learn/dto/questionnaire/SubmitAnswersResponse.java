package com.adap.learn.dto.questionnaire;

import lombok.*;

import java.util.List;

/**
 * ✅ SubmitAnswersResponse
 *
 * Represents the response returned after a student submits their answers
 * to a questionnaire. It includes scoring information, accuracy percentage,
 * and optionally a breakdown of each question's correctness.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitAnswersResponse {

    private String questionnaireId;
    private String studentId;

    private int correctCount;

    private int totalQuestions;

    private double scorePercentage;

    private String performanceLevel;

    private List<AnswerResult> answers;

    /**
     * Nested DTO representing per-question evaluation result.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnswerResult {

        private String questionId;

        private String text;

        private String selectedAnswer;

  
        private String correctAnswer;


        private boolean correct;
    }
}
