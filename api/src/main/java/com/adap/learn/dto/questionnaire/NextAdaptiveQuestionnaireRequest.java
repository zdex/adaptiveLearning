package com.adap.learn.dto.questionnaire;
import jakarta.validation.constraints.NotBlank;
public record NextAdaptiveQuestionnaireRequest(
        @NotBlank String studentId,
        @NotBlank String subject,
        String targetDifficulty // e.g., EASY/MEDIUM/HARD
) {}
