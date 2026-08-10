package com.nypunya.course.service;

import com.nypunya.course.dto.CourseRequest;
import com.nypunya.course.dto.CourseResponse;
import com.nypunya.course.entity.Course;
import com.nypunya.course.entity.CourseStatus;
import com.nypunya.course.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
public class CourseService {
    private final CourseRepository repository;
    public CourseService(CourseRepository repository) { this.repository = repository; }

    @Transactional
    public CourseResponse create(CourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle().trim());
        course.setDescription(request.getDescription());
        course.setTrainerId(request.getTrainerId());
        return new CourseResponse(repository.save(course));
    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> list(CourseStatus status, Pageable pageable) {
        Page<Course> page = status == null ? repository.findAll(pageable) : repository.findByStatus(status, pageable);
        return page.map(CourseResponse::new);
    }

    @Transactional(readOnly = true)
    public CourseResponse get(Long id) {
        return repository.findById(id).map(CourseResponse::new)
                .orElseThrow(() -> new NoSuchElementException("Course not found: " + id));
    }
}
