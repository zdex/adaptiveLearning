package com.adap.learn.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    // *** FIX: Added the Many-to-One relationship to the User entity ***
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private Integer age;

    @Column
    private String gradeLevel;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    // NOTE: Removed email, password, role, and active fields as they belong to the User entity.
}