package com.nypunya.assignment.repository;

import com.nypunya.assignment.entity.Assignment;
import com.nypunya.assignment.entity.AssignmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
 Page<Assignment> findByCourseId(Long courseId,Pageable pageable);
 Page<Assignment> findByTrainerId(Long trainerId,Pageable pageable);
 Page<Assignment> findByCourseIdAndStatus(Long courseId,AssignmentStatus status,Pageable pageable);
}
