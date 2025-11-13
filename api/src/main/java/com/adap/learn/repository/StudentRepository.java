package com.adap.learn.repository;

import com.adap.learn.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Student entities.
 * Extends JpaRepository to inherit standard CRUD operations.
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    /**
     * Finds all Student profiles associated with a specific parent User ID.
     * The method name 'findByUser_UserId' uses the User entity's primary key (userId)
     * through the 'user' field in the Student entity.
     */
    List<StudentEntity> findByStudentId(Long userId);

    /**
     * Finds a specific student by their ID, ensuring they belong to the given parent User ID
     * (used for secure access control).
     */
    Optional<StudentEntity> findByStudentIdAndParent_UserId(Long studentId, Long userId);
}