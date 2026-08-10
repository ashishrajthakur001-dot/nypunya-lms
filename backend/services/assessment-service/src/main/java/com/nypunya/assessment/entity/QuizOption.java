package com.nypunya.assessment.entity;
import javax.persistence.*;
@Entity @Table(name="quiz_options",indexes=@Index(name="idx_option_question",columnList="question_id"))
public class QuizOption { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(name="question_id",nullable=false) private Long questionId; @Column(nullable=false,length=1000) private String text; @Column(name="is_correct",nullable=false) private boolean correct; public Long getId(){return id;} public Long getQuestionId(){return questionId;} public void setQuestionId(Long v){questionId=v;} public String getText(){return text;} public void setText(String v){text=v;} public boolean isCorrect(){return correct;} public void setCorrect(boolean v){correct=v;} }
