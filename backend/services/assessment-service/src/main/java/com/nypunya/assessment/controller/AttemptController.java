package com.nypunya.assessment.controller;

import com.nypunya.assessment.entity.QuizAnswer;
import com.nypunya.assessment.entity.QuizAttempt;
import com.nypunya.assessment.service.AttemptService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/assessment")
public class AttemptController {
 private final AttemptService service;
 public AttemptController(AttemptService service){this.service=service;}
 private Long student(Principal p){return Long.valueOf(p.getName());}
 @PostMapping("/quizzes/{quizId}/attempts") @ResponseStatus(HttpStatus.CREATED)
 public QuizAttempt start(@PathVariable Long quizId,Principal p){return service.start(quizId,student(p));}
 @PostMapping("/attempts/{attemptId}/answers")
 public QuizAnswer answer(@PathVariable Long attemptId,@RequestParam Long questionId,@RequestParam Long optionId,Principal p){return service.answer(attemptId,student(p),questionId,optionId);}
 @PostMapping("/attempts/{attemptId}/submit")
 public QuizAttempt submit(@PathVariable Long attemptId,Principal p){return service.submit(attemptId,student(p));}
 @GetMapping("/attempts/{attemptId}")
 public QuizAttempt get(@PathVariable Long attemptId,Principal p){return service.get(attemptId,student(p));}
}
