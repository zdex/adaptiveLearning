package com.adap.learn.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuestionEntity {
    @Id @GeneratedValue(strategy=GenerationType.UUID)
    private String id;
    private String text;
    private String type; // MCQ/FREE_TEXT
    private String difficulty;

    @ManyToOne
    private QuestionnaireEntity questionnaire;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<QuestionOptionEntity> options;

    private String correctOptionId; // optional for auto-grading
}
