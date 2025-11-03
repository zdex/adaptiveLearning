package com.adap.learn.service;

import com.adap.learn.model.User;
import com.adap.learn.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method is automatically called by Spring Security during authentication.
     * It loads a user from the database using their username (or email).
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()) // authentication uses email
                .password(user.getPassword())
                .authorities(getAuthorities(user))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isActive())
                .build();
    }


    /**
     * Returns user roles (authorities). For now, assume every user has "ROLE_USER".
     * If your User entity has roles, map them here.
     */
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        if (user.getRole() != null) {
            // Example: user.role = "ADMIN" or "USER"
            return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
