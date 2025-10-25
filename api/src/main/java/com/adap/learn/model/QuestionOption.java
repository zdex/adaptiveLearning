package com.adap.learn.model;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuestionOption {
    @Id @GeneratedValue(strategy=GenerationType.UUID)
    private String id;
    private String text;

    @ManyToOne
    private Question question;
}
