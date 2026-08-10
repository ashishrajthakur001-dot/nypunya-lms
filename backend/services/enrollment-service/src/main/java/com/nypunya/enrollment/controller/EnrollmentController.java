package com.nypunya.enrollment.controller;

import com.nypunya.enrollment.entity.Enrollment; import com.nypunya.enrollment.service.EnrollmentService; import org.springframework.http.HttpStatus; import org.springframework.security.access.AccessDeniedException; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/enrollments") public class EnrollmentController {
 private final EnrollmentService service; public EnrollmentController(EnrollmentService service){this.service=service;}
 @PostMapping("/courses/{courseId}") @ResponseStatus(HttpStatus.CREATED) public Enrollment enroll(@PathVariable Long courseId,Authentication auth){
  if(auth==null||auth.getAuthorities().stream().noneMatch(a->a.getAuthority().equals("ROLE_STUDENT"))) throw new AccessDeniedException("Student role required");
  return service.enroll(Long.valueOf(auth.getName()),courseId);
 }
 @GetMapping("/{id}") public Enrollment get(@PathVariable Long id,Authentication auth){
  Enrollment e=service.get(id); String role=auth.getAuthorities().iterator().next().getAuthority();
  if("ROLE_ADMIN".equals(role)||"ROLE_TRAINER".equals(role)||String.valueOf(e.getStudentId()).equals(auth.getName())) return e;
  throw new AccessDeniedException("Not allowed to view this enrollment");
 }
}
