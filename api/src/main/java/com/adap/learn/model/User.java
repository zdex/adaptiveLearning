package com.adap.learn.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name="users")
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private boolean enabled;
    private String otp;            // simple OTP column (rotate/expire in real impl)
    private String roles;          // e.g. "ROLE_STUDENT,ROLE_TEACHER"
}
