package com.nypunya.lms.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> validation(MethodArgumentNotValidException ex){
        Map<String,Object> body=new LinkedHashMap<>(); body.put("timestamp", Instant.now().toString()); body.put("status",400); body.put("code","VALIDATION_ERROR"); body.put("message","Request validation failed"); return body;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> illegal(IllegalArgumentException ex){
        Map<String,Object> body=new LinkedHashMap<>(); body.put("timestamp", Instant.now().toString()); body.put("status",400); body.put("code","BAD_REQUEST"); body.put("message",ex.getMessage()); return body;
    }
}
