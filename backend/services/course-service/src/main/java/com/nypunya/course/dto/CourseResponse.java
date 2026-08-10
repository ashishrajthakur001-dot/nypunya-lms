package com.nypunya.course.dto;

import com.nypunya.course.entity.Course;
import com.nypunya.course.entity.CourseStatus;

public class CourseResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final CourseStatus status;
    private final Long trainerId;

    public CourseResponse(Course c) {
        this.id = c.getId(); this.title = c.getTitle(); this.description = c.getDescription();
        this.status = c.getStatus(); this.trainerId = c.getTrainerId();
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public CourseStatus getStatus() { return status; }
    public Long getTrainerId() { return trainerId; }
}
