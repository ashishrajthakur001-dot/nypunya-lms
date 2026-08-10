package com.nypunya.enrollment.service;

import com.nypunya.enrollment.entity.Enrollment;
import com.nypunya.enrollment.repository.EnrollmentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
public class EnrollmentService {
 private final EnrollmentRepository repository;
 public EnrollmentService(EnrollmentRepository repository){this.repository=repository;}
 @Transactional
 public Enrollment enroll(Long studentId, Long courseId){
   if(studentId==null || courseId==null) throw new IllegalArgumentException("Student and course are required");
   if(repository.findByStudentIdAndCourseId(studentId,courseId).isPresent()) throw new IllegalStateException("Already enrolled");
   Enrollment e=new Enrollment(); e.setStudentId(studentId); e.setCourseId(courseId);
   try{return repository.save(e);}catch(DataIntegrityViolationException ex){throw new IllegalStateException("Already enrolled");}
 }
 @Transactional(readOnly=true)
 public Enrollment get(Long id){return repository.findById(id).orElseThrow(()->new NoSuchElementException("Enrollment not found"));}
}
