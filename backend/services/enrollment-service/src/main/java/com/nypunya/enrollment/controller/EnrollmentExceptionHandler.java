package com.nypunya.enrollment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class EnrollmentExceptionHandler {
 private Map<String,Object> body(HttpStatus status,String message){Map<String,Object> b=new LinkedHashMap<>();b.put("status",status.value());b.put("error",status.getReasonPhrase());b.put("message",message);return b;}
 @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST) public Map<String,Object> badRequest(IllegalArgumentException e){return body(HttpStatus.BAD_REQUEST,e.getMessage());}
 @ExceptionHandler(IllegalStateException.class) @ResponseStatus(HttpStatus.CONFLICT) public Map<String,Object> conflict(IllegalStateException e){return body(HttpStatus.CONFLICT,e.getMessage());}
 @ExceptionHandler(SecurityException.class) @ResponseStatus(HttpStatus.FORBIDDEN) public Map<String,Object> forbidden(SecurityException e){return body(HttpStatus.FORBIDDEN,e.getMessage());}
 @ExceptionHandler(AccessDeniedException.class) @ResponseStatus(HttpStatus.FORBIDDEN) public Map<String,Object> denied(AccessDeniedException e){return body(HttpStatus.FORBIDDEN,e.getMessage());}
 @ExceptionHandler(java.util.NoSuchElementException.class) @ResponseStatus(HttpStatus.NOT_FOUND) public Map<String,Object> notFound(java.util.NoSuchElementException e){return body(HttpStatus.NOT_FOUND,e.getMessage());}
}
