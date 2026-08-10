package com.nypunya.enrollment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(NoSuchElementException.class) @ResponseStatus(HttpStatus.NOT_FOUND) public Map<String,Object> notFound(NoSuchElementException e){return body(404,e.getMessage());}
 @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST) public Map<String,Object> badRequest(IllegalArgumentException e){return body(400,e.getMessage());}
 @ExceptionHandler(IllegalStateException.class) @ResponseStatus(HttpStatus.CONFLICT) public Map<String,Object> conflict(IllegalStateException e){return body(409,e.getMessage());}
 @ExceptionHandler(SecurityException.class) @ResponseStatus(HttpStatus.FORBIDDEN) public Map<String,Object> security(SecurityException e){return body(403,e.getMessage());}
 @ExceptionHandler(AccessDeniedException.class) @ResponseStatus(HttpStatus.FORBIDDEN) public Map<String,Object> denied(AccessDeniedException e){return body(403,e.getMessage());}
 private Map<String,Object> body(int status,String message){Map<String,Object> m=new LinkedHashMap<>();m.put("status",status);m.put("message",message);return m;}
}
