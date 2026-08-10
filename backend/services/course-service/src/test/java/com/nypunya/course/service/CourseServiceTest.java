package com.nypunya.course.service;

import com.nypunya.course.dto.CourseRequest;
import com.nypunya.course.entity.Course;
import com.nypunya.course.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock CourseRepository repository;
    @InjectMocks CourseService service;

    @Test
    void createsCourseAndTrimsTitle() {
        CourseRequest request = new CourseRequest();
        request.setTitle("  Java Spring Boot  ");
        request.setDescription("Backend course");
        when(repository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));

        assertEquals("Java Spring Boot", service.create(request).getTitle());
        verify(repository).save(any(Course.class));
    }

    @Test
    void missingCourseFailsClearly() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(java.util.NoSuchElementException.class, () -> service.get(99L));
    }
}
