package com.adap.learn.repository;

import com.adap.learn.model.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ✅ StudentAnswerRepository
 *
 * Stores and retrieves all submitted student answers.
 * Used for adaptive learning analysis.
 */
@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

    /**
     * Find all answers submitted by a specific student for a questionnaire.
     */
    List<StudentAnswer> findByStudentIdAndQuestionnaireId(String studentId, String questionnaireId);

    /**
     * Find all answers by student (across all subjects).
     */
    List<StudentAnswer> findByStudentId(String studentId);

    /**
     * Calculate average score (correct answers / total) for a student.
     */
    @Query("SELECT COUNT(a) FROM StudentAnswer a WHERE a.studentId = ?1 AND a.correct = true")
    long countCorrectAnswersByStudent(String studentId);
}
