package com.adap.learn.dto.questionnaire;

import java.util.List;

/**
 * Immutable request object for submitting questionnaire answers.
 * Uses Java 17+ record syntax for simplicity and thread-safety.
 */
public record SubmitAnswersRequest(
        String studentId,
        String questionnaireId,
        List<AnswerDTO> answers
) {
    /**
     * Nested record representing a single answer.
     */
    public record AnswerDTO(
            String questionId,
            Integer selectedIndex
    ) { }
}

