package com.adap.learn.repository;

import com.adap.learn.entity.QuestionOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ✅ QuestionOptionRepository
 *
 * Manages multiple-choice options linked to each question.
 */
@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOptionEntity, String> {

    /**
     * Find all options associated with a specific question.
     */
    @Query("SELECT o FROM QuestionOptionEntity o WHERE o.question.id = ?1")
    List<QuestionOptionEntity> findByQuestionId(String questionId);

    /**
     * Delete all options belonging to a question (used when updating questions).
     */
    void deleteByQuestionId(String questionId);
}
