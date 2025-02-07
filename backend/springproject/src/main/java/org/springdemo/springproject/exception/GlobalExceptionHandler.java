package org.springdemo.springproject.exception;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.springproject.service.LogApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final LogApiService logApiService;
    private final HttpServletRequest request;

    // Handles validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        response.put("message", "Validation failed");
        response.put("errors", fieldErrors);
        response.put("path", request.getDescription(false).replace("uri=", ""));

        log.error("Validation Exception: {}", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, WebRequest request) {
        int statusCode = getStatusFromException(ex);

        // Log  errors here not in aspect class
        logApiService.saveLog(
                this.request.getMethod(),
                request.getDescription(false).replace("uri=", ""),
                String.valueOf(statusCode),
                0L,
                "ERROR",
                ex.getMessage()
        );

        return buildErrorResponse(ex.getMessage(), HttpStatus.valueOf(statusCode), request);
    }

    private int getStatusFromException(Exception ex) {
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        return (responseStatus != null) ? responseStatus.value().value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status, WebRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        response.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(response, status);
    }
}
