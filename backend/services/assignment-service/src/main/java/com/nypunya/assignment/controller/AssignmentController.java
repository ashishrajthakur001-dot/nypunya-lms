package com.nypunya.assignment.controller;

import com.nypunya.assignment.dto.AssignmentRequest;
import com.nypunya.assignment.entity.Assignment;
import com.nypunya.assignment.entity.AssignmentStatus;
import com.nypunya.assignment.service.AssignmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
 private final AssignmentService service;
 public AssignmentController(AssignmentService service){this.service=service;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Assignment create(@Valid @RequestBody AssignmentRequest request,Authentication auth){requireRole(auth,"TRAINER");return service.create(request,userId(auth));}
 @GetMapping("/{id}") public Assignment get(@PathVariable Long id,Authentication auth){Assignment a=service.get(id);if(hasRole(auth,"ADMIN")||hasRole(auth,"TRAINER")||(hasRole(auth,"STUDENT")&&a.getStatus()==AssignmentStatus.PUBLISHED))return a;throw new AccessDeniedException("Assignment is not available");}
 @GetMapping("/course/{courseId}") public Page<Assignment> list(@PathVariable Long courseId,@RequestParam(required=false) AssignmentStatus status,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size,Authentication auth){if(!hasRole(auth,"ADMIN")&&!hasRole(auth,"TRAINER")&&!hasRole(auth,"STUDENT"))throw new AccessDeniedException("Authenticated user required");if(hasRole(auth,"STUDENT")&&(status==AssignmentStatus.DRAFT||status==AssignmentStatus.CLOSED))throw new AccessDeniedException("Students may only view published assignments");AssignmentStatus effectiveStatus=hasRole(auth,"STUDENT")?AssignmentStatus.PUBLISHED:status;return service.list(courseId,effectiveStatus,PageRequest.of(pageSize(page),sizeLimit(size)));}
 @PostMapping("/{id}/publish") public Assignment publish(@PathVariable Long id,Authentication auth){requireRole(auth,"TRAINER");return service.publishOwned(id,userId(auth));}
 @PostMapping("/{id}/close") public Assignment close(@PathVariable Long id,Authentication auth){requireRole(auth,"TRAINER");return service.closeOwned(id,userId(auth));}
 private Long userId(Authentication a){try{return Long.valueOf(a.getName());}catch(Exception e){throw new AccessDeniedException("Invalid authenticated user");}}
 private void requireRole(Authentication a,String role){if(!hasRole(a,role))throw new AccessDeniedException(role+" role required");}
 private boolean hasRole(Authentication a,String role){return a!=null&&a.isAuthenticated()&&a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_"+role));}
 private int pageSize(int v){if(v<0)throw new IllegalArgumentException("page must be >= 0");return v;}
 private int sizeLimit(int v){if(v<1||v>100)throw new IllegalArgumentException("size must be between 1 and 100");return v;}
}
