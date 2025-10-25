package com.adap.learn.util;

import java.util.*;

public class JsonUtils {
    public record AiQ(String difficulty, List<Q> questions) {}
    public record Q(String text, String type, List<String> options, int answerIndex) {}

    public static AiQ parseAIQuestionnaire(String json) {
        // For brevity, use a naive parse; in real code use Jackson
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<?,?> m = mapper.readValue(json, Map.class);
            String difficulty = (String)m.getOrDefault("difficulty", "MEDIUM");
            List<Map<String,Object>> qs = (List<Map<String,Object>>) m.get("questions");
            List<Q> list = new ArrayList<>();
            if (qs != null) {
                for (var q : qs) {
                    String text = (String) q.get("text");
                    String type = (String) q.getOrDefault("type","MCQ");
                    List<String> opts = (List<String>) q.getOrDefault("options", List.of());
                    int ans = ((Number) q.getOrDefault("answerIndex", 0)).intValue();
                    list.add(new Q(text,type,opts,ans));
                }
            }
            return new AiQ(difficulty, list);
        } catch (Exception e) {
            // fallback: single dummy question
            return new AiQ("MEDIUM", List.of(new Q("Fallback Q?", "MCQ", List.of("A","B","C","D"), 0)));
        }
    }

    public static String historyToJson(java.util.List<?> answers) {
        // simple summary
        return "{\"attempts\":"+ (answers==null?0:answers.size()) + "}";
    }
}
