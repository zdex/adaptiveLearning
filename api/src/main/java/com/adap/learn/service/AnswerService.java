package com.adap.learn.service;


import com.adap.learn.dto.questionnaire.SubmitAnswersRequest;
import com.adap.learn.dto.questionnaire.SubmitAnswersResponse;
import com.adap.learn.entity.AnswerEntity;
import com.adap.learn.repository.AnswerRepository;

import com.adap.learn.utils.DifficultyLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public SubmitAnswersResponse submitAnswers(SubmitAnswersRequest request) {
        int total = request.answers().size();
        int correct = 0;

        // Save each answer and evaluate correctness
        for (SubmitAnswersRequest.AnswerDTO dto : request.answers()) {
            // Load question from DB or existing session (simplified here)
            // Example correctness check: use a stored correctIndex or mock logic
            boolean isCorrect = dto.selectedIndex() != null && dto.selectedIndex() == 1; // Demo

            if (isCorrect) correct++;

            AnswerEntity entity = new AnswerEntity();
            entity.setQuestionId(dto.questionId());
            entity.setSelectedIndex(dto.selectedIndex());
            entity.setCorrect(isCorrect);
            entity.setStudentId(request.studentId());
            entity.setQuestionnaireId(request.questionnaireId());
            answerRepository.save(entity);
        }

        double percent = total == 0 ? 0 : ((double) correct / total) * 100;
        DifficultyLevel next = getNextDifficulty(percent);
        
        return SubmitAnswersResponse.builder()
                .totalQuestions(total)
                .correctCount(correct)
                .percentage(percent)
                .nextDifficulty(String.valueOf(next))
                .message("Answers saved. Next difficulty: " + next)
                .build();
    }

    private DifficultyLevel getNextDifficulty(double percent) {
        if (percent < 40) return DifficultyLevel.EASY;
        if (percent <= 75) return DifficultyLevel.MEDIUM;
        return DifficultyLevel.HARD;
    }
}
