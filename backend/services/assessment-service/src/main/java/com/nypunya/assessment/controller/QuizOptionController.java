package com.nypunya.assessment.controller;
import com.nypunya.assessment.dto.QuizOptionRequest; import com.nypunya.assessment.entity.QuizOption; import com.nypunya.assessment.service.QuizAuthoringService; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import javax.validation.Valid; import java.security.Principal; import java.util.List;
@RestController @RequestMapping("/api/assessment/quizzes") public class QuizOptionController { private final QuizAuthoringService service; public QuizOptionController(QuizAuthoringService s){service=s;} private Long user(Principal p){return Long.valueOf(p.getName());}
 @PostMapping("/{quizId}/questions/{questionId}/options") @ResponseStatus(HttpStatus.CREATED) public QuizOption add(@PathVariable Long quizId,@PathVariable Long questionId,@Valid @RequestBody QuizOptionRequest r,Principal p){return service.addOption(quizId,questionId,r,user(p));}
 @GetMapping("/{quizId}/questions/{questionId}/options") public List<QuizOption> list(@PathVariable Long quizId,@PathVariable Long questionId,Principal p){return service.options(quizId,questionId,user(p));}
}
