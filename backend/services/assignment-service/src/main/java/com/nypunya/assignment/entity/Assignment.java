package com.nypunya.assignment.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="assignments", indexes={@Index(name="idx_assignment_course",columnList="course_id"),@Index(name="idx_assignment_trainer",columnList="trainer_id")})
public class Assignment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=160) private String title;
 @Column(length=4000) private String description;
 @Column(name="course_id",nullable=false) private Long courseId;
 @Column(name="trainer_id",nullable=false) private Long trainerId;
 @Column(name="due_at") private LocalDateTime dueAt;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private AssignmentStatus status=AssignmentStatus.DRAFT;
 @Column(name="created_at",nullable=false) private LocalDateTime createdAt;
 @PrePersist void prePersist(){if(createdAt==null)createdAt=LocalDateTime.now();}
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public Long getCourseId(){return courseId;} public void setCourseId(Long v){courseId=v;} public Long getTrainerId(){return trainerId;} public void setTrainerId(Long v){trainerId=v;} public LocalDateTime getDueAt(){return dueAt;} public void setDueAt(LocalDateTime v){dueAt=v;} public AssignmentStatus getStatus(){return status;} public void setStatus(AssignmentStatus v){status=v;} public LocalDateTime getCreatedAt(){return createdAt;}
}
