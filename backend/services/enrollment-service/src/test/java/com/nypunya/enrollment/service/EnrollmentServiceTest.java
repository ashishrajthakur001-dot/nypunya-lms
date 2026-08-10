package com.nypunya.enrollment.service;

import com.nypunya.enrollment.entity.Enrollment;
import com.nypunya.enrollment.entity.EnrollmentStatus;
import com.nypunya.enrollment.repository.EnrollmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {
 @Mock EnrollmentRepository repository;
 @InjectMocks EnrollmentService service;

 @Test void createsEnrollment(){
  when(repository.findByStudentIdAndCourseId(1L,10L)).thenReturn(Optional.empty());
  when(repository.save(any(Enrollment.class))).thenAnswer(i->i.getArgument(0));
  Enrollment e=service.enroll(1L,10L);
  assertEquals(1L,e.getStudentId()); assertEquals(10L,e.getCourseId()); assertEquals(EnrollmentStatus.ACTIVE,e.getStatus());
 }
 @Test void rejectsDuplicate(){
  Enrollment e=new Enrollment();e.setStudentId(1L);e.setCourseId(10L);
  when(repository.findByStudentIdAndCourseId(1L,10L)).thenReturn(Optional.of(e));
  assertThrows(IllegalStateException.class,()->service.enroll(1L,10L));
 }
 @Test void reactivatesCancelled(){
  Enrollment e=new Enrollment();e.setStudentId(1L);e.setCourseId(10L);e.setStatus(EnrollmentStatus.CANCELLED);
  when(repository.findByStudentIdAndCourseId(1L,10L)).thenReturn(Optional.of(e));
  when(repository.save(e)).thenReturn(e);
  assertEquals(EnrollmentStatus.ACTIVE,service.enroll(1L,10L).getStatus());
 }
 @Test void cannotCancelCompleted(){
  Enrollment e=new Enrollment();e.setStudentId(1L);e.setCourseId(10L);e.setStatus(EnrollmentStatus.COMPLETED);
  when(repository.findById(5L)).thenReturn(Optional.of(e));
  assertThrows(IllegalStateException.class,()->service.cancel(5L,1L));
 }
 @Test void cannotCancelAnotherStudentsEnrollment(){
  Enrollment e=new Enrollment();e.setStudentId(2L);e.setCourseId(10L);e.setStatus(EnrollmentStatus.ACTIVE);
  when(repository.findById(5L)).thenReturn(Optional.of(e));
  assertThrows(SecurityException.class,()->service.cancel(5L,1L));
 }
}
