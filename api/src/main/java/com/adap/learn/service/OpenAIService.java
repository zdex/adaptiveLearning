package com.adap.learn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service @RequiredArgsConstructor
public class OpenAIService {
    @Value("${openai.api.key}")
    private String apiKey;
    private final WebClient client = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader("Content-Type", "application/json")
            .build();
    public String generateQuestions(String subject, String grade, String difficulty, String historyJson) {
        return getTestText();
    }
    /*public String generateQuestions(String subject, String grade, String difficulty, String historyJson) {
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
      //  var content = (Map<String,Object>) ((Map<String,Object>)choices.get(0).get("message")).get("content");
        // Some APIs return string content; if Map cast fails, fallback:
       // if (content == null) {
        var text = (String)((Map<String,Object>)choices.get(0).get("message")).get("content");
        return text;
        //}
        //return content.toString();
    }*/

    private String getTestText() {
        return "{\n" +
                "  \"difficulty\" : \"MEDIUM\",\n" +
                "  \"questions\" : [ {\n" +
                "    \"text\" : \"What is the value of 7 × 8?\",\n" +
                "    \"type\" : \"MCQ\",\n" +
                "    \"options\" : [ \"54\", \"56\", \"58\", \"60\" ],\n" +
                "    \"answerIndex\" : 1\n" +
                "  }, {\n" +
                "    \"text\" : \"Which of the following fractions is equivalent to 3/4?\",\n" +
                "    \"type\" : \"MCQ\",\n" +
                "    \"options\" : [ \"6/8\", \"2/3\", \"1/2\", \"5/6\" ],\n" +
                "    \"answerIndex\" : 0\n" +
                "  }, {\n" +
                "    \"text\" : \"What is the perimeter of a rectangle with length 10 cm and width 4 cm?\",\n" +
                "    \"type\" : \"MCQ\",\n" +
                "    \"options\" : [ \"28 cm\", \"40 cm\", \"24 cm\", \"20 cm\" ],\n" +
                "    \"answerIndex\" : 0\n" +
                "  }, {\n" +
                "    \"text\" : \"If x + 5 = 12, what is the value of x?\",\n" +
                "    \"type\" : \"MCQ\",\n" +
                "    \"options\" : [ \"7\", \"5\", \"12\", \"17\" ],\n" +
                "    \"answerIndex\" : 0\n" +
                "  }, {\n" +
                "    \"text\" : \"What is the area of a triangle with a base of 6 cm and a height of 4 cm?\",\n" +
                "    \"type\" : \"MCQ\",\n" +
                "    \"options\" : [ \"12 cm²\", \"24 cm²\", \"18 cm²\", \"10 cm²\" ],\n" +
                "    \"answerIndex\" : 0\n" +
                "  }, {\n" +
                "    \"text\" : \"Which of the following is a prime number?\",\n" +
                "    \"type\" : \"MCQ\",\n" +
                "    \"options\" : [ \"9\", \"15\", \"13\", \"21\" ],\n" +
                "    \"answerIndex\" : 2\n" +
                "  }, {\n" +
                "    \"text\" : \"What is 15% of 200?\",\n" +
                "    \"type\" : \"MCQ\",\n" +
                "    \"options\" : [ \"30\", \"25\", \"20\", \"15\" ],\n" +
                "    \"answerIndex\" : 0\n" +
                "  }, {\n" +
                "    \"text\" : \"What is the least common multiple (LCM) of 4 and 5?\",\n" +
                "    \"type\" : \"MCQ\",\n" +
                "    \"options\" : [ \"10\", \"20\", \"15\", \"25\" ],\n" +
                "    \"answerIndex\" : 1\n" +
                "  } ]\n" +
                "}";
    }
}
