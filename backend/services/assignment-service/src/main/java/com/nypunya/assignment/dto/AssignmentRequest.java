package com.nypunya.assignment.dto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class AssignmentRequest {
 @NotBlank @Size(max=160) private String title;
 @Size(max=4000) private String description;
 @NotNull private Long courseId;
 private LocalDateTime dueAt;
 public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public Long getCourseId(){return courseId;} public void setCourseId(Long v){courseId=v;} public LocalDateTime getDueAt(){return dueAt;} public void setDueAt(LocalDateTime v){dueAt=v;}
}
