package com.nypunya.assessment.repository;
import com.nypunya.assessment.entity.QuizAnswer; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List; import java.util.Optional;
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer,Long> { List<QuizAnswer> findByAttemptId(Long attemptId); Optional<QuizAnswer> findByAttemptIdAndQuestionId(Long attemptId,Long questionId); }
