package com.adap.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// Assuming this DTO structure based on your existing register logic

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    // Added role field
    private String role;
}