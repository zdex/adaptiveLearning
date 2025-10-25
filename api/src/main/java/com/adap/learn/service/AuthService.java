package com.adap.learn.service;

import com.adap.learn.dto.auth.LoginRequest;
import com.adap.learn.dto.auth.RegisterRequest;
import com.adap.learn.model.User;
import com.adap.learn.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository users;
    private final MailService mail;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void register(RegisterRequest req) {
        var user = User.builder()
                .email(req.email())
                .firstName(req.firstName())
                .lastName(req.lastName())
                .passwordHash(encoder.encode(req.password()))
                .enabled(false)
                .roles("ROLE_STUDENT")
                .otp(String.format("%06d", (int)(Math.random()*1_000_000)))
                .build();
        users.save(user);
        mail.sendOtp(user.getEmail(), user.getOtp());
    }

    public String login(LoginRequest req, JwtService jwt) {
        var user = users.findByEmail(req.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!user.isEnabled()) throw new RuntimeException("User not verified");
        if (!encoder.matches(req.password(), user.getPasswordHash()))
            throw new BadCredentialsException("Bad credentials");
        return jwt.generateToken(user.getEmail());
    }

    public boolean verifyOtp(String email, String otp) {
        var user = users.findByEmail(email).orElseThrow();
        if (user.getOtp()!=null && user.getOtp().equals(otp)) {
            user.setEnabled(true);
            user.setOtp(null);
            users.save(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var u = users.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(u.getEmail())
                .password(u.getPasswordHash())
                .roles(u.getRoles().split(","))
                .disabled(!u.isEnabled())
                .build();
    }
}
