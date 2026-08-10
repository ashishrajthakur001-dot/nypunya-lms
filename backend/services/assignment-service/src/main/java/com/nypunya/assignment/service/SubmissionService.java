package com.nypunya.assignment.service;

import com.nypunya.assignment.entity.*;
import com.nypunya.assignment.repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service public class SubmissionService {
 private final SubmissionRepository submissions; private final AssignmentRepository assignments;
 public SubmissionService(SubmissionRepository submissions,AssignmentRepository assignments){this.submissions=submissions;this.assignments=assignments;}
 @Transactional public Submission submit(Long assignmentId,Long studentId,String content){
  if(content==null||content.trim().isEmpty())throw new IllegalArgumentException("Submission content is required");
  Assignment a=assignments.findById(assignmentId).orElseThrow(()->new java.util.NoSuchElementException("Assignment not found"));
  if(a.getStatus()!=AssignmentStatus.PUBLISHED)throw new IllegalStateException("Assignment is not open for submission");
  if(a.getDueAt()!=null&&LocalDateTime.now().isAfter(a.getDueAt()))throw new IllegalStateException("Submission deadline has passed");
  if(submissions.findByAssignmentIdAndStudentId(assignmentId,studentId).isPresent())throw new IllegalStateException("Submission already exists");
  Submission s=new Submission();s.setAssignmentId(assignmentId);s.setStudentId(studentId);s.setContent(content.trim());
  try{return submissions.save(s);}catch(DataIntegrityViolationException e){throw new IllegalStateException("Submission already exists");}
 }
 @Transactional public Submission grade(Long id,Integer score,String feedback){
  if(score==null||score<0||score>100)throw new IllegalArgumentException("Score must be between 0 and 100");
  Submission s=submissions.findById(id).orElseThrow(()->new java.util.NoSuchElementException("Submission not found"));
  s.setScore(score);s.setFeedback(feedback);s.setStatus(SubmissionStatus.GRADED);s.setGradedAt(LocalDateTime.now());return submissions.save(s);
 }
}
