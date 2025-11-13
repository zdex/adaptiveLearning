package com.adap.learn.controller;

import com.adap.learn.dto.StudentDTO;
import com.adap.learn.entity.ParentEntity;
import com.adap.learn.service.AuthService;
import com.adap.learn.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Tag(name = "Student Management", description = "Endpoints for creating and managing student profiles.")
@SecurityRequirement(name = "BearerAuth")
public class StudentController {

    private final StudentService studentService;
    private final AuthService authService;

    public StudentController(StudentService studentService, AuthService authService) {
        this.studentService = studentService;
        this.authService = authService;
    }

    @Operation(summary = "Create multiple student profiles for the authenticated parent")
    @PostMapping
    public ResponseEntity<List<StudentDTO>> createStudents(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody List<StudentDTO> studentDtos) {

        // 1. Get the authenticated Parent User from the token
        ParentEntity parent = authService.getAuthenticatedUser(authHeader);

        // 2. Save the students and link them to the parent
        List<StudentDTO> savedStudents = studentService.saveStudents(studentDtos, parent);

        return ResponseEntity.ok(savedStudents);
    }
}