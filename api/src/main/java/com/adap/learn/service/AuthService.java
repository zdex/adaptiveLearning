package com.adap.learn.service;

import com.adap.learn.dto.*;
import com.adap.learn.entity.ParentEntity; // Use User model
import com.adap.learn.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Register a new user (parent/account owner)
     */
    public AuthResponse register(RegisterRequestDTO request) {
        ParentEntity user = ParentEntity.builder() // Changed from Student to User
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("PARENT")
                .active(true)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        ParentDto parentDto = parentDtoMapper(user, userDetails);
        return new AuthResponse("User registered successfully", token, parentDto);
    }

    /**
     * Authenticate user and return JWT token
     */
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        ParentEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));

        ParentDto parentDto = parentDtoMapper(user, userDetails);
        String token = jwtService.generateToken(user.getFirstName() + user.getLastName());

        return new AuthResponse("Login successful", token, parentDto);
    }

    private ParentDto parentDtoMapper(ParentEntity user, UserDetails userDetails) {
        Collection<? extends GrantedAuthority> userAuthorities = this.getAuthorities(user);
        List<StudentDTO> studentDtos = new ArrayList<>();
        if(null != user.getStudents() && !user.getStudents().isEmpty()) {
            user.getStudents().forEach(student -> {
                StudentDTO studentDto = StudentDTO.builder()
                        .studentId(student.getStudentId()).grade(student.getGrade()).age(student.getAge()).emailId(student.getEmailId()).dateOfBirth(student.getDateOfBirth())
                        .firstName(student.getFirstName()).lastName(student.getLastName()).build();
                studentDtos.add(studentDto);
            });
        }
        return ParentDto.builder()
                .emailId(userDetails.getUsername())
                .roles(null).firstName(user.getFirstName()).lastName(user.getLastName()).credentialsExpired(userDetails.isCredentialsNonExpired()).accountExpired(userDetails.isAccountNonExpired()).dateOfBirth(user.getDateOfBirth())
                .parentId(user.getUserId()).disabled(userDetails.isEnabled()).accountLocked(userDetails.isAccountNonLocked()).students(studentDtos).userAuthorities(userAuthorities).build();
    }

    /**
     * Returns user roles (authorities). For now, assume every user has "ROLE_USER".
     * If your User entity has roles, map them here.
     */
    private Collection<? extends GrantedAuthority> getAuthorities(ParentEntity user) {
        if (user.getRole() != null) {
            // Example: user.role = "ADMIN" or "USER"
            return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString().toUpperCase()));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Get current logged-in user (parent/account owner) info from token
     */
    public ParentEntity getAuthenticatedUser(String authHeader) { // *** FIX: Changed return type to User ***
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);

        // This correctly finds the User (Parent) account by email.
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}