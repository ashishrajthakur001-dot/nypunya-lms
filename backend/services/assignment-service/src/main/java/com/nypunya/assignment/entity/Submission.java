package com.nypunya.assignment.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="submissions", uniqueConstraints=@UniqueConstraint(name="uk_assignment_student", columnNames={"assignment_id","student_id"}), indexes={@Index(name="idx_submission_assignment",columnList="assignment_id"),@Index(name="idx_submission_student",columnList="student_id")})
public class Submission {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="assignment_id",nullable=false) private Long assignmentId;
 @Column(name="student_id",nullable=false) private Long studentId;
 @Column(name="content",nullable=false,length=10000) private String content;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private SubmissionStatus status=SubmissionStatus.SUBMITTED;
 @Column(name="submitted_at",nullable=false) private LocalDateTime submittedAt;
 @Column(name="graded_at") private LocalDateTime gradedAt;
 @Column(name="score") private Integer score;
 @Column(name="feedback",length=4000) private String feedback;
 @PrePersist void prePersist(){if(submittedAt==null)submittedAt=LocalDateTime.now();}
 public Long getId(){return id;} public Long getAssignmentId(){return assignmentId;} public void setAssignmentId(Long v){assignmentId=v;} public Long getStudentId(){return studentId;} public void setStudentId(Long v){studentId=v;} public String getContent(){return content;} public void setContent(String v){content=v;} public SubmissionStatus getStatus(){return status;} public void setStatus(SubmissionStatus v){status=v;} public LocalDateTime getSubmittedAt(){return submittedAt;} public LocalDateTime getGradedAt(){return gradedAt;} public void setGradedAt(LocalDateTime v){gradedAt=v;} public Integer getScore(){return score;} public void setScore(Integer v){score=v;} public String getFeedback(){return feedback;} public void setFeedback(String v){feedback=v;}
}
