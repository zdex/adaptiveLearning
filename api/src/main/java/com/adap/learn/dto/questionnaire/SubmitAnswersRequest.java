package com.adap.learn.dto.questionnaire;
import java.util.List;
public record SubmitAnswersRequest(
        String questionnaireId,
        String studentId,
        List<Answer> answers
) {
    public record Answer(String questionId, String selectedOptionId, String freeText) {}
}
