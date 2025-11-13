package com.adap.learn.repository;

import com.adap.learn.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ✅ QuestionRepository
 *
 * Handles all operations related to questions inside a questionnaire.
 */
@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, String> {

    /**
     * Fetch all questions belonging to a given questionnaire.
     */
    @Query("SELECT q FROM QuestionEntity q WHERE q.questionnaire.id = ?1")
    List<QuestionEntity> findByQuestionnaireId(String questionnaireId);

    /**
     * Search question text using case-insensitive partial match.
     */
    @Query("SELECT q FROM QuestionEntity q WHERE LOWER(q.text) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<QuestionEntity> searchByText(String keyword);
}
