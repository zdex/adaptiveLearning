package com.adap.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private long studentId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String grade;
    private Date dateOfBirth;
    private String emailId;
}
