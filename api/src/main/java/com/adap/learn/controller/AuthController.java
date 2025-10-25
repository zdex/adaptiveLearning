package com.adap.learn.controller;

import com.adap.learn.dto.auth.LoginRequest;
import com.adap.learn.dto.auth.OtpRequest;
import com.adap.learn.dto.auth.RegisterRequest;
import com.adap.learn.service.AuthService;
import com.adap.learn.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor 

public class AuthController {
    private final AuthService auth;
    private final JwtService jwt;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        auth.register(req);
        return ResponseEntity.ok().body("Registered. Check email for OTP.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verify(@RequestBody OtpRequest req) {
        return auth.verifyOtp(req.email(), req.otp())
                ? ResponseEntity.ok("Verified.")
                : ResponseEntity.badRequest().body("Invalid OTP.");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String token = auth.login(req, jwt);
        return ResponseEntity.ok().body(java.util.Map.of("token", token));
    }
}
