package com.nypunya.assessment.controller;

import com.nypunya.assessment.entity.*;
import com.nypunya.assessment.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/assessment/quizzes")
public class QuizController {
 private final QuizRepository quizzes; private final QuizQuestionRepository questions; private final QuizOptionRepository options;
 public QuizController(QuizRepository quizzes,QuizQuestionRepository questions,QuizOptionRepository options){this.quizzes=quizzes;this.questions=questions;this.options=options;}
 private Long user(Principal p){return Long.valueOf(p.getName());}
 @PostMapping @ResponseStatus(HttpStatus.CREATED)
 public Quiz create(@RequestBody Quiz quiz,Principal p){quiz.setTrainerId(user(p));quiz.setStatus(QuizStatus.DRAFT);return quizzes.save(quiz);}
 @GetMapping("/{id}/questions") public List<QuizQuestion> questions(@PathVariable Long id){if(!quizzes.existsById(id))throw new NoSuchElementException("Quiz not found");return questions.findByQuizIdOrderByQuestionOrderAsc(id);}
 @PostMapping("/{id}/questions") @ResponseStatus(HttpStatus.CREATED)
 public QuizQuestion addQuestion(@PathVariable Long id,@RequestBody @Valid QuizQuestion question,Principal p){Quiz q=quizzes.findById(id).orElseThrow(()->new NoSuchElementException("Quiz not found"));if(!q.getTrainerId().equals(user(p)))throw new SecurityException("Not quiz owner");if(q.getStatus()!=QuizStatus.DRAFT)throw new IllegalStateException("Only draft quizzes can be edited");question.setQuizId(id);return questions.save(question);}
 @PostMapping("/{id}/publish") public Quiz publish(@PathVariable Long id,Principal p){Quiz q=quizzes.findById(id).orElseThrow(()->new NoSuchElementException("Quiz not found"));if(!q.getTrainerId().equals(user(p)))throw new SecurityException("Not quiz owner");if(questions.findByQuizIdOrderByQuestionOrderAsc(id).isEmpty())throw new IllegalStateException("Quiz must contain at least one question");if(q.getStatus()==QuizStatus.CLOSED)throw new IllegalStateException("Closed quiz cannot be published");q.setStatus(QuizStatus.PUBLISHED);return quizzes.save(q);}
 @PostMapping("/{id}/close") public Quiz close(@PathVariable Long id,Principal p){Quiz q=quizzes.findById(id).orElseThrow(()->new NoSuchElementException("Quiz not found"));if(!q.getTrainerId().equals(user(p)))throw new SecurityException("Not quiz owner");q.setStatus(QuizStatus.CLOSED);return quizzes.save(q);}
}
