package com.nypunya.enrollment.controller;

import com.nypunya.enrollment.entity.Enrollment;
import com.nypunya.enrollment.service.EnrollmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
 private final EnrollmentService service;
 public EnrollmentController(EnrollmentService service){this.service=service;}
 @PostMapping("/courses/{courseId}") @ResponseStatus(HttpStatus.CREATED)
 public Enrollment enroll(@PathVariable Long courseId,Authentication auth){requireRole(auth,"STUDENT");return service.enroll(userId(auth),courseId);}
 @GetMapping("/{id}") public Enrollment get(@PathVariable Long id,Authentication auth){Enrollment e=service.get(id);requireCanView(auth,e);return e;}
 @GetMapping("/mine") public Page<Enrollment> mine(Authentication auth,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){requireRole(auth,"STUDENT");return service.listForStudent(userId(auth),PageRequest.of(normalizePage(page),normalizeSize(size)));}
 @GetMapping("/course/{courseId}") public Page<Enrollment> byCourse(@PathVariable Long courseId,Authentication auth,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){if(!hasRole(auth,"ADMIN")&&!hasRole(auth,"TRAINER"))throw new AccessDeniedException("Trainer or admin role required");return service.listForCourse(courseId,PageRequest.of(normalizePage(page),normalizeSize(size)));}
 @PostMapping("/{id}/cancel") public Enrollment cancel(@PathVariable Long id,Authentication auth){requireRole(auth,"STUDENT");return service.cancel(id,userId(auth));}
 @PostMapping("/{id}/complete") public Enrollment complete(@PathVariable Long id,Authentication auth){if(!hasRole(auth,"ADMIN")&&!hasRole(auth,"TRAINER"))throw new AccessDeniedException("Trainer or admin role required");return service.complete(id);}
 private Long userId(Authentication a){try{return Long.valueOf(a.getName());}catch(Exception e){throw new AccessDeniedException("Invalid authenticated user");}}
 private void requireRole(Authentication a,String role){if(!hasRole(a,role))throw new AccessDeniedException(role+" role required");}
 private boolean hasRole(Authentication a,String role){return a!=null&&a.isAuthenticated()&&a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_"+role));}
 private void requireCanView(Authentication a,Enrollment e){if(hasRole(a,"ADMIN")||hasRole(a,"TRAINER")||(hasRole(a,"STUDENT")&&String.valueOf(e.getStudentId()).equals(a.getName())))return;throw new AccessDeniedException("Not allowed to view this enrollment");}
 private int normalizePage(int p){if(p<0)throw new IllegalArgumentException("page must be >= 0");return p;}
 private int normalizeSize(int s){if(s<1||s>100)throw new IllegalArgumentException("size must be between 1 and 100");return s;}
}
