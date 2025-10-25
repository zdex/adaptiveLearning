package com.adap.learn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service @RequiredArgsConstructor
public class OpenAIService {
    @Value("${openai.api.key:}")
    private String apiKey;

    private final WebClient client = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader("Content-Type", "application/json")
            .build();

    public String generateQuestions(String subject, String grade, String difficulty, String historyJson) {
        String system = """
                You are a quiz generator. Output strict JSON with fields:
                { "difficulty": "...", "questions": [ { "text": "...", "type": "MCQ", "options": ["..."], "answerIndex": 1 } ] }
                """;
        String user = """
                Subject: %s
                Grade: %s
                Difficulty: %s
                Student history (JSON): %s
                Create 8 questions, MCQ if possible.
                """.formatted(subject, grade, difficulty==null?"MEDIUM":difficulty, historyJson==null?"{}":historyJson);

        // Use Chat Completions (works well with WebClient). Adjust model if needed.
        var payload = Map.of(
                "model", "gpt-4o-mini",
                "messages", new Object[] {
                        Map.of("role","system","content",system),
                        Map.of("role","user","content",user)
                },
                "temperature", 0.4
        );

        var res = client.post()
                .uri("/chat/completions")
                .headers(h -> h.setBearerAuth(apiKey))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (res == null) throw new RuntimeException("OpenAI response is null");
        var choices = (java.util.List<Map<String,Object>>) res.get("choices");
        var content = (Map<String,Object>) ((Map<String,Object>)choices.get(0).get("message")).get("content");
        // Some APIs return string content; if Map cast fails, fallback:
        if (content == null) {
            var text = (String)((Map<String,Object>)choices.get(0).get("message")).get("content");
            return text;
        }
        return content.toString();
    }
}
