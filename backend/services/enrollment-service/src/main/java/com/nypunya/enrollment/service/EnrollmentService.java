package com.nypunya.enrollment.service;

import com.nypunya.enrollment.entity.Enrollment;
import com.nypunya.enrollment.entity.EnrollmentStatus;
import com.nypunya.enrollment.repository.EnrollmentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
public class EnrollmentService {
 private final EnrollmentRepository repository;
 public EnrollmentService(EnrollmentRepository repository){this.repository=repository;}
 @Transactional
 public Enrollment enroll(Long studentId,Long courseId){
  if(studentId==null||courseId==null) throw new IllegalArgumentException("Student and course are required");
  Enrollment existing=repository.findByStudentIdAndCourseId(studentId,courseId).orElse(null);
  if(existing!=null){
   if(existing.getStatus()==EnrollmentStatus.CANCELLED){existing.setStatus(EnrollmentStatus.ACTIVE);return repository.save(existing);}
   throw new IllegalStateException("Already enrolled");
  }
  Enrollment e=new Enrollment();e.setStudentId(studentId);e.setCourseId(courseId);
  try{return repository.save(e);}catch(DataIntegrityViolationException ex){throw new IllegalStateException("Already enrolled");}
 }
 @Transactional(readOnly=true) public Enrollment get(Long id){return repository.findById(id).orElseThrow(()->new NoSuchElementException("Enrollment not found"));}
 @Transactional(readOnly=true) public Page<Enrollment> listForStudent(Long studentId,Pageable pageable){return repository.findByStudentId(studentId,pageable);}
 @Transactional(readOnly=true) public Page<Enrollment> listForCourse(Long courseId,Pageable pageable){return repository.findByCourseId(courseId,pageable);}
 @Transactional public Enrollment cancel(Long id,Long studentId){Enrollment e=get(id);if(!e.getStudentId().equals(studentId))throw new SecurityException("Enrollment ownership required");if(e.getStatus()==EnrollmentStatus.COMPLETED)throw new IllegalStateException("Completed enrollment cannot be cancelled");if(e.getStatus()==EnrollmentStatus.CANCELLED)return e;e.setStatus(EnrollmentStatus.CANCELLED);return repository.save(e);}
 @Transactional public Enrollment complete(Long id){Enrollment e=get(id);if(e.getStatus()==EnrollmentStatus.CANCELLED)throw new IllegalStateException("Cancelled enrollment cannot be completed");e.setStatus(EnrollmentStatus.COMPLETED);return repository.save(e);}
}
