package com.nypunya.assignment.controller;

import com.nypunya.assignment.dto.SubmissionRequest;
import com.nypunya.assignment.entity.Submission;
import com.nypunya.assignment.service.SubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
 private final SubmissionService service;
 public SubmissionController(SubmissionService service){this.service=service;}
 @PostMapping("/assignments/{assignmentId}") @ResponseStatus(HttpStatus.CREATED)
 public Submission submit(@PathVariable Long assignmentId,@Valid @RequestBody SubmissionRequest request,Authentication auth){
  requireRole(auth,"STUDENT"); return service.submit(assignmentId,userId(auth),request.getContent());
 }
 @PostMapping("/{id}/grade")
 public Submission grade(@PathVariable Long id,@RequestParam Integer score,@RequestParam(required=false) String feedback,Authentication auth){
  if(!hasRole(auth,"TRAINER")&&!hasRole(auth,"ADMIN"))throw new AccessDeniedException("Trainer or admin role required");
  return service.grade(id,score,feedback);
 }
 private Long userId(Authentication a){try{return Long.valueOf(a.getName());}catch(Exception e){throw new AccessDeniedException("Invalid authenticated user");}}
 private void requireRole(Authentication a,String role){if(!hasRole(a,role))throw new AccessDeniedException(role+" role required");}
 private boolean hasRole(Authentication a,String role){return a!=null&&a.isAuthenticated()&&a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_"+role));}
}
