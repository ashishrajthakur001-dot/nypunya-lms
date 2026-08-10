package com.nypunya.assessment.repository;
import com.nypunya.assessment.entity.QuizAttempt; import com.nypunya.assessment.entity.QuizAttemptStatus; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt,Long> { long countByQuizIdAndStudentId(Long quizId,Long studentId); Optional<QuizAttempt> findByIdAndStudentId(Long id,Long studentId); }
