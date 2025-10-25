package com.adap.learn.repository;
import com.adap.learn.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    java.util.Optional<User> findByEmail(String email);
}

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, String> {}
public interface QuestionRepository extends JpaRepository<Question, String> {}
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, String> {}
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
    java.util.List<StudentAnswer> findByStudentIdAndQuestionnaireId(String studentId, String questionnaireId);
}
