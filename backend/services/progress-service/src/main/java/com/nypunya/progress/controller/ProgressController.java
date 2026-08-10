package com.nypunya.progress.controller;
import com.nypunya.progress.entity.CourseProgress; import com.nypunya.progress.service.ProgressService; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.security.Principal; import java.util.List;
@RestController @RequestMapping("/api/progress") public class ProgressController { private final ProgressService service; public ProgressController(ProgressService service){this.service=service;} private Long user(Principal p){return Long.valueOf(p.getName());}
 @PostMapping("/courses/{courseId}/initialize") @ResponseStatus(HttpStatus.CREATED) public CourseProgress initialize(@PathVariable Long courseId,@RequestParam Integer totalItems,Principal p){return service.initialize(user(p),courseId,totalItems);}
 @PutMapping("/courses/{courseId}") public CourseProgress update(@PathVariable Long courseId,@RequestParam Integer completedItems,@RequestParam Integer totalItems,Principal p){return service.update(user(p),courseId,completedItems,totalItems);}
 @GetMapping("/courses/{courseId}") public CourseProgress get(@PathVariable Long courseId,Principal p){return service.get(user(p),courseId);}
 @GetMapping("/courses") public List<CourseProgress> list(Principal p){return service.list(user(p));}
}
