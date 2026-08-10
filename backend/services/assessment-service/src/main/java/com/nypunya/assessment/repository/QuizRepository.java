package com.nypunya.assessment.repository;
import com.nypunya.assessment.entity.Quiz; import org.springframework.data.jpa.repository.JpaRepository;
public interface QuizRepository extends JpaRepository<Quiz,Long> {}
