package com.nypunya.course.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CourseRequest {
    @NotBlank @Size(max = 160)
    private String title;
    @Size(max = 4000)
    private String description;
    private Long trainerId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getTrainerId() { return trainerId; }
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; }
}
