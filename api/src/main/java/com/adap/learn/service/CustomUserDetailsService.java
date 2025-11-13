package com.adap.learn.service;

import com.adap.learn.entity.ParentEntity;
import com.adap.learn.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        ParentEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(email) // authentication uses email
                .password(user.getPassword())
                //.authorities(getAuthorities(user))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                //.disabled(!user.isActive())
                .build();
    }

}
