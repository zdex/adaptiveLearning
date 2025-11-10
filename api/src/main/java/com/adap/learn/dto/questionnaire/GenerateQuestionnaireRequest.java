package com.adap.learn.dto.questionnaire;
import jakarta.validation.constraints.NotBlank;
public record GenerateQuestionnaireRequest(
        @NotBlank String subject,
        @NotBlank String grade,
        String style, // e.g., MCQ/short-answer
        @NotBlank String nextDifficultyLevel
) {}
