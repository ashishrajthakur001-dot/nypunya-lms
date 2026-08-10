package com.nypunya.assessment.repository;
import com.nypunya.assessment.entity.QuizQuestion; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long> { List<QuizQuestion> findByQuizIdOrderByQuestionOrderAsc(Long quizId); }
