package com.adap.learn.controller;

import com.adap.learn.dto.questionnaire.*;
import com.adap.learn.service.AnswerService;
import com.adap.learn.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service/questionnaire")
@RequiredArgsConstructor
public class QuestionnaireController {

    private final QuestionnaireService service;
    private final AnswerService answerService;

    // 🔹 1. Generate questionnaire
    @PostMapping("/generate")
    public ResponseEntity<GenerateQuestionnaireResponse> generate(
            @RequestBody GenerateQuestionnaireRequest req,
            @RequestHeader(value = "X-Owner", required = false) String owner) {

        GenerateQuestionnaireResponse response = service.generate(req, owner);
        return ResponseEntity.ok(response);
    }

}
