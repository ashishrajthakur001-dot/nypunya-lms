package com.nypunya.course.controller;

import com.nypunya.course.dto.CourseRequest;
import com.nypunya.course.dto.CourseResponse;
import com.nypunya.course.entity.CourseStatus;
import com.nypunya.course.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/courses")
@Validated
public class CourseController {
    private final CourseService service;
    public CourseController(CourseService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse create(@Valid @RequestBody CourseRequest request) { return service.create(request); }

    @GetMapping
    public Page<CourseResponse> list(@RequestParam(required = false) CourseStatus status, Pageable pageable) {
        return service.list(status, pageable);
    }

    @GetMapping("/{id}")
    public CourseResponse get(@PathVariable Long id) { return service.get(id); }
}
