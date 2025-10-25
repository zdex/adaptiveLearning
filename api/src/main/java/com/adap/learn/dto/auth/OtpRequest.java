package com.adap.learn.dto.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record OtpRequest(@Email String email, @NotBlank String otp) {}
