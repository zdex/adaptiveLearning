package com.adap.learn.controller;


import com.adap.learn.dto.questionnaire.SubmitAnswersRequest;
import com.adap.learn.dto.questionnaire.SubmitAnswersResponse;
import com.adap.learn.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/submit-answers")
    public ResponseEntity<SubmitAnswersResponse> submitAnswers(@RequestBody SubmitAnswersRequest request) {
        return ResponseEntity.ok(answerService.submitAnswers(request));
    }
}
