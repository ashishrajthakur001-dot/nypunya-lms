package com.nypunya.assessment.dto;
import javax.validation.constraints.NotBlank; import javax.validation.constraints.NotNull; import javax.validation.constraints.Size;
public class QuizOptionRequest { @NotBlank @Size(max=1000) private String text; @NotNull private Boolean correct; public String getText(){return text;} public void setText(String v){text=v;} public Boolean getCorrect(){return correct;} public void setCorrect(Boolean v){correct=v;} }
