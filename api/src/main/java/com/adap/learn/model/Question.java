package com.adap.learn.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Question {
    @Id @GeneratedValue(strategy=GenerationType.UUID)
    private String id;
    private String text;
    private String type; // MCQ/FREE_TEXT

    @ManyToOne
    private Questionnaire questionnaire;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<QuestionOption> options;

    private String correctOptionId; // optional for auto-grading
}
