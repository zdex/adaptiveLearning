package com.adap.learn.repository;

import com.adap.learn.model.QuestionnaireSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ✅ QuestionnaireSessionRepository
 *
 * Stores historical questionnaire sessions for each student.
 * Each session links a student, a questionnaire, and performance score.
 */
@Repository
public interface QuestionnaireSessionRepository extends JpaRepository<QuestionnaireSession, Long> {

    /**
     * Find all sessions by student ID.
     */
    List<QuestionnaireSession> findByStudentId(String studentId);

    /**
     * Fetch the last N sessions for a student (used to adapt next difficulty).
     */
    @Query("SELECT s FROM QuestionnaireSession s WHERE s.studentId = ?1 ORDER BY s.completedAt DESC LIMIT ?2")
    List<QuestionnaireSession> findRecentSessions(String studentId, int limit);

    /**
     * Calculate student average accuracy across all sessions.
     */
    @Query("SELECT AVG(s.scorePercentage) FROM QuestionnaireSession s WHERE s.studentId = ?1")
    Double getAverageScoreForStudent(String studentId);
}
