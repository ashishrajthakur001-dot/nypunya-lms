package com.nypunya.enrollment.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CourseClient {
 private final RestTemplate restTemplate; private final String courseServiceUrl;
 public CourseClient(RestTemplate restTemplate,@Value("${services.course.url:http://localhost:8082}") String courseServiceUrl){this.restTemplate=restTemplate;this.courseServiceUrl=courseServiceUrl;}
 public boolean existsAndPublished(Long courseId,String authorization){
  try{HttpHeaders h=new HttpHeaders();h.set("Authorization",authorization);ResponseEntity<String> r=restTemplate.exchange(courseServiceUrl+"/api/courses/"+courseId,HttpMethod.GET,new HttpEntity<>(h),String.class);return r.getStatusCode().is2xxSuccessful();}
  catch(RuntimeException e){return false;}
 }
}
