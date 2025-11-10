package com.adap.learn.dto.questionnaire;

import com.adap.learn.model.Question;
import lombok.*;

import java.util.List;

/**
 * ✅ GenerateQuestionnaireResponse
 *
 * Represents the payload returned to the frontend after a new questionnaire
 * is generated (either initial or adaptive). This structure is designed
 * to serialize cleanly as JSON and display nicely in Swagger UI.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateQuestionnaireResponse {

    
    private String questionnaireId;

    private String subject;


    private String grade;

  
    private String difficulty;


    private List<QuestionDTO> questions;

    /**
     * Inner DTO representing individual questions within a questionnaire.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
  
    public static class QuestionDTO {

   
        private String questionId;

     
        private String text;

      
        private String type;

    
        private List<String> options;


        private Integer correctIndex;
    }
}
