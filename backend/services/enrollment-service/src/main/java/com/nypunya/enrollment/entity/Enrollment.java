package com.nypunya.enrollment.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="enrollments", uniqueConstraints=@UniqueConstraint(name="uk_student_course", columnNames={"student_id","course_id"}), indexes={@Index(name="idx_enrollment_student", columnList="student_id"),@Index(name="idx_enrollment_course",columnList="course_id")})
public class Enrollment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="student_id",nullable=false) private Long studentId;
 @Column(name="course_id",nullable=false) private Long courseId;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private EnrollmentStatus status=EnrollmentStatus.ACTIVE;
 @Column(name="enrolled_at",nullable=false) private LocalDateTime enrolledAt;
 @PrePersist void prePersist(){ if(enrolledAt==null) enrolledAt=LocalDateTime.now(); }
 public Long getId(){return id;} public Long getStudentId(){return studentId;} public void setStudentId(Long v){studentId=v;} public Long getCourseId(){return courseId;} public void setCourseId(Long v){courseId=v;} public EnrollmentStatus getStatus(){return status;} public void setStatus(EnrollmentStatus v){status=v;} public LocalDateTime getEnrolledAt(){return enrolledAt;}
}
