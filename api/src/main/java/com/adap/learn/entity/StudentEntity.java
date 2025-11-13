package com.adap.learn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    // *** FIX: Added the Many-to-One relationship to the User entity ***
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private ParentEntity parent;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private Integer age;

    @Column
    private String grade;

    @Column
    private String emailId;

    @Column
    private Date dateOfBirth;

    public String getFullName() {
        return firstName + " " + lastName;
    }


}

