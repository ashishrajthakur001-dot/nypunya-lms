package com.nypunya.course.controller;

import com.nypunya.course.dto.CourseRequest;
import com.nypunya.course.dto.CourseResponse;
import com.nypunya.course.entity.CourseStatus;
import com.nypunya.course.security.RoleGuard;
import com.nypunya.course.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/courses")
@Validated
public class CourseController {
    private final CourseService service;
    private final RoleGuard roleGuard;
    public CourseController(CourseService service, RoleGuard roleGuard) { this.service = service; this.roleGuard = roleGuard; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse create(@Valid @RequestBody CourseRequest request, Authentication authentication) {
        if (!roleGuard.hasRole(authentication, "TRAINER") && !roleGuard.hasRole(authentication, "ADMIN")) {
            throw new AccessDeniedException("Trainer or admin role required");
        }
        Long trainerId = roleGuard.hasRole(authentication, "TRAINER") ? Long.valueOf(authentication.getName()) : request.getTrainerId();
        if (trainerId == null) throw new IllegalArgumentException("Trainer id is required for admin course creation");
        return service.create(request, trainerId);
    }

    @GetMapping
    public Page<CourseResponse> list(@RequestParam(required = false) CourseStatus status, Pageable pageable) {
        return service.list(status, pageable);
    }

    @GetMapping("/{id}")
    public CourseResponse get(@PathVariable Long id) { return service.get(id); }
}
