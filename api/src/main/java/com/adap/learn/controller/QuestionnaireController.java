package com.adap.learn.controller;

import com.adap.learn.dto.questionnaire.*;
import com.adap.learn.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service/questionnaire")
@RequiredArgsConstructor
public class QuestionnaireController {

    private final QuestionnaireService service;

    // 🔹 1. Generate questionnaire
    @PostMapping("/generate")
    public ResponseEntity<GenerateQuestionnaireResponse> generate(
            @RequestBody GenerateQuestionnaireRequest req,
            @RequestHeader(value = "X-Owner", required = false) String owner) {

        GenerateQuestionnaireResponse response = service.generate(req, owner);
        return ResponseEntity.ok(response);
    }

    // 🔹 2. Submit answers
    @PostMapping("/submit")
    public ResponseEntity<SubmitAnswersResponse> submit(@RequestBody SubmitAnswersRequest req) {
        SubmitAnswersResponse response = service.submit(req);
        return ResponseEntity.ok(response);
    }

    // 🔹 3. Next adaptive questionnaire
    @PostMapping("/next-adaptive")
    public ResponseEntity<GenerateQuestionnaireResponse> nextAdaptive(
            @RequestBody NextAdaptiveQuestionnaireRequest req) {

        GenerateQuestionnaireResponse response = service.nextAdaptive(req);
        return ResponseEntity.ok(response);
    }
}
