package com.adap.learn.service;

import com.adap.learn.dto.questionnaire.*;
import com.adap.learn.model.*;
import com.adap.learn.repository.*;
import com.adap.learn.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service @RequiredArgsConstructor
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepo;
    private final QuestionRepository questionRepo;
    private final QuestionOptionRepository optionRepo;
    private final StudentAnswerRepository answerRepo;
    private final OpenAIService openAI;

    public GenerateQuestionnaireResponse generate(GenerateQuestionnaireRequest req, String ownerEmail) {
        String aiJson = openAI.generateQuestions(req.subject(), req.grade(), "MEDIUM", "{}");
        var parsed = JsonUtils.parseAIQuestionnaire(aiJson);

        var questionnaire = Questionnaire.builder()
                .subject(req.subject())
                .grade(req.grade())
                .difficulty(parsed.difficulty())
                .createdAt(Instant.now())
                .owner(null) // set owner if needed
                .build();
        questionnaire = questionnaireRepo.save(questionnaire);

        for (var q : parsed.questions()) {
            var ent = Question.builder()
                    .text(q.text())
                    .type(q.type())
                    .questionnaire(questionnaire).build();
            ent = questionRepo.save(ent);
            int idx = 0;
            for (var optText : q.options()) {
                var opt = QuestionOption.builder().text(optText).question(ent).build();
                opt = optionRepo.save(opt);
                if (idx == q.answerIndex()) ent.setCorrectOptionId(opt.getId());
                idx++;
            }
            questionRepo.save(ent);
        }
        return new GenerateQuestionnaireResponse(questionnaire.getId(), questionnaire.getSubject(),
                questionnaire.getGrade(), questionnaire.getDifficulty());
    }

    public SubmitAnswersResponse submit(SubmitAnswersRequest req) {
        int correct = 0;
        for (var a : req.answers()) {
            boolean isCorrect = false;
            if (a.selectedOptionId()!=null) {
                var q = questionRepo.findById(a.questionId()).orElse(null);
                isCorrect = q!=null && a.selectedOptionId().equals(q.getCorrectOptionId());
                if (isCorrect) correct++;
            }
            var ans = StudentAnswer.builder()
                    .questionnaireId(req.questionnaireId())
                    .questionId(a.questionId())
                    .studentId(req.studentId())
                    .selectedOptionId(a.selectedOptionId())
                    .freeText(a.freeText())
                    .correct(isCorrect)
                    .build();
            answerRepo.save(ans);
        }
        return new SubmitAnswersResponse(req.questionnaireId(), req.studentId(), correct, req.answers().size());
    }

    public GenerateQuestionnaireResponse nextAdaptive(NextAdaptiveQuestionnaireRequest req) {
        // Aggregate history by correctness per topic (simplified: per questionnaire)
        var prev = answerRepo.findByStudentIdAndQuestionnaireId(req.studentId(), req.subject()); // adapt as needed
        var historyJson = JsonUtils.historyToJson(prev);

        String targetDifficulty = req.targetDifficulty()!=null ? req.targetDifficulty() : "MEDIUM";
        String aiJson = openAI.generateQuestions(req.subject(), "AUTO", targetDifficulty, historyJson);
        var parsed = JsonUtils.parseAIQuestionnaire(aiJson);

        var questionnaire = Questionnaire.builder()
                .subject(req.subject())
                .grade("AUTO")
                .difficulty(parsed.difficulty())
                .createdAt(java.time.Instant.now())
                .build();
        questionnaire = questionnaireRepo.save(questionnaire);

        for (var q : parsed.questions()) {
            var ent = Question.builder().text(q.text()).type(q.type()).questionnaire(questionnaire).build();
            ent = questionRepo.save(ent);
            int idx = 0;
            for (var optText : q.options()) {
                var opt = QuestionOption.builder().text(optText).question(ent).build();
                opt = optionRepo.save(opt);
                if (idx == q.answerIndex()) ent.setCorrectOptionId(opt.getId());
                idx++;
            }
            questionRepo.save(ent);
        }

        return new GenerateQuestionnaireResponse(
                questionnaire.getId(), questionnaire.getSubject(), questionnaire.getGrade(), questionnaire.getDifficulty());
    }
}
