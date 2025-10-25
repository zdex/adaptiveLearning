package com.adap.learn.repository;

import com.adap.learn.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ✅ QuestionnaireRepository
 *
 * Provides CRUD and custom methods for Questionnaire entities.
 */
@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, String> {

    /**
     * Find questionnaires by subject and grade.
     */
    List<Questionnaire> findBySubjectAndGrade(String subject, String grade);

    /**
     * Find all questionnaires created by a specific user.
     */
    @Query("SELECT q FROM Questionnaire q WHERE q.owner.email = ?1")
    List<Questionnaire> findAllByOwnerEmail(String ownerEmail);

    /**
     * Fetch latest questionnaires by creation time.
     */
    @Query("SELECT q FROM Questionnaire q ORDER BY q.createdAt DESC")
    List<Questionnaire> findRecentQuestionnaires();
}
