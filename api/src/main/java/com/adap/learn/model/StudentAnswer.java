package com.adap.learn.model;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentAnswer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String questionnaireId;
    private String questionId;
    private String studentId;
    private String selectedOptionId;
    private String freeText;
    private boolean correct;
}
