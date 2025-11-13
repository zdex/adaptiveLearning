package com.adap.learn.controller;

import com.adap.learn.dto.AuthRequest;
import com.adap.learn.dto.AuthResponse;
import com.adap.learn.dto.RegisterRequestDTO;
import com.adap.learn.entity.ParentEntity;
import com.adap.learn.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user (Parent/Account Owner)")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Login user and get JWT token")
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "Get details of the currently authenticated user (Parent/Account Owner)")
    @ApiResponse(responseCode = "200", description = "Access granted")
    @GetMapping("/me")
    public ResponseEntity<ParentEntity> getAuthenticatedUser(@RequestHeader("Authorization") String token) {
        // Now calling the updated service method
        return ResponseEntity.ok(authService.getAuthenticatedUser(token));
    }
}