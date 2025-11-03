package com.adap.learn.repository;

import com.adap.learn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ✅ UserRepository
 *
 * Handles database operations for User entities — registration,
 * authentication, OTP verification, and lookups by email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email (unique field).
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user with this email already exists.
     */
    boolean existsByEmail(String email);

    /**
     * Fetch only enabled (verified) users.
     */
    @Query("SELECT u FROM User u WHERE u.active = true")
    java.util.List<User> findAllVerifiedUsers();
}
