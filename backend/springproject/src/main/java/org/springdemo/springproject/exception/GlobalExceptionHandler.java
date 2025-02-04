package org.springdemo.springproject.exception;

import lombok.AllArgsConstructor;
import org.springdemo.springproject.service.LogExceptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {


    private final LogExceptionService logExceptionService;


 //  Handle specific exception (BookNotFoundException)
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
       // logEntryService.saveErrorLog(ex.getMessage(), "handleBookNotFoundException", ex); AOP does this now
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND,request);
    }


    // Handle MethodArgumentNotValidException (Validation errors) not logged into db
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError error : fieldErrors) {
            errors.put(error.getField(), error.getDefaultMessage());  // Field and the validation message
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

   //Errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
       logExceptionService.saveErrorLog(ex.getMessage(), "GlobalExceptionHandler");
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }



    // Helper method to build structured error response
    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status, WebRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        response.put("path", request.getDescription(false).replace("uri=", "")); // Extracts request URI

        return new ResponseEntity<>(response, status);
    }
}


