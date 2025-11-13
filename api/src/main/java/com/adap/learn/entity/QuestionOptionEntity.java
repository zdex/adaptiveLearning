package com.adap.learn.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuestionOptionEntity {
    @Id @GeneratedValue(strategy=GenerationType.UUID)
    private String id;
    private String text;

    @ManyToOne
    private QuestionEntity question;
}
