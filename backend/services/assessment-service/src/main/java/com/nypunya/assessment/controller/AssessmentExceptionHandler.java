package com.nypunya.assessment.controller;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.time.LocalDateTime; import java.util.Map; import java.util.NoSuchElementException;
@RestControllerAdvice public class AssessmentExceptionHandler { private ResponseEntity<Map<String,Object>> error(HttpStatus s,String m){return ResponseEntity.status(s).body(Map.of("timestamp",LocalDateTime.now().toString(),"status",s.value(),"error",s.getReasonPhrase(),"message",m));}
 @ExceptionHandler(NoSuchElementException.class) ResponseEntity<Map<String,Object>> notFound(NoSuchElementException e){return error(HttpStatus.NOT_FOUND,e.getMessage());}
 @ExceptionHandler(SecurityException.class) ResponseEntity<Map<String,Object>> forbidden(SecurityException e){return error(HttpStatus.FORBIDDEN,e.getMessage());}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<Map<String,Object>> bad(IllegalArgumentException e){return error(HttpStatus.BAD_REQUEST,e.getMessage());}
 @ExceptionHandler(IllegalStateException.class) ResponseEntity<Map<String,Object>> conflict(IllegalStateException e){return error(HttpStatus.CONFLICT,e.getMessage());}
}
