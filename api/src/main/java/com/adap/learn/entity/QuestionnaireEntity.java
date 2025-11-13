package com.adap.learn.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuestionnaireEntity {
    @Id @GeneratedValue(strategy=GenerationType.UUID)
    private String id;
    private String subject;
    private String grade;
    private String difficulty; // EASY/MEDIUM/HARD
    private Instant createdAt;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<QuestionEntity> questions;

    @ManyToOne(fetch = FetchType.LAZY)
    private ParentEntity owner;
}
