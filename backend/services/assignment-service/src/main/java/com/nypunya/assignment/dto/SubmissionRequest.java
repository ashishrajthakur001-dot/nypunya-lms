package com.nypunya.assignment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SubmissionRequest {
 @NotBlank @Size(max=10000) private String content;
 public String getContent(){return content;} public void setContent(String v){content=v;}
}
