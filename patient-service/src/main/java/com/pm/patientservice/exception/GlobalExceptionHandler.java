package com.pm.patientservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<Object> handleApiException(ApiException ex, HttpServletRequest request) {

    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", ex.getStatus().value());
    body.put("error", ex.getStatus().getReasonPhrase());
    body.put("message", ex.getMessage());
    body.put("path", request.getRequestURI());

    return new ResponseEntity<>(body, ex.getStatus());
  }
}
