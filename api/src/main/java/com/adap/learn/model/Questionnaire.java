package com.adap.learn.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Questionnaire {
    @Id @GeneratedValue(strategy=GenerationType.UUID)
    private String id;
    private String subject;
    private String grade;
    private String difficulty; // EASY/MEDIUM/HARD
    private Instant createdAt;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Question> questions;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student owner;
}
