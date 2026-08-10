package com.nypunya.assignment.repository;

import com.nypunya.assignment.entity.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {
 Optional<Submission> findByAssignmentIdAndStudentId(Long assignmentId,Long studentId);
 Page<Submission> findByAssignmentId(Long assignmentId,Pageable pageable);
 Page<Submission> findByStudentId(Long studentId,Pageable pageable);
}
