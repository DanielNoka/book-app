package org.springdemo.springproject.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.springproject.service.LogApiService;
import org.springdemo.springproject.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.HashMap;
import java.util.Map;
import static org.springdemo.springproject.util.Constants.FAIL;
import org.springframework.security.access.AccessDeniedException;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice // ControllerAdvice + ResponseBody(ensure JSON response)
public class GlobalExceptionHandler {

    private final LogApiService logApiService;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Forbidden");
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.FORBIDDEN.value());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles entity not found exception
     */
    @ExceptionHandler({EntityNotFoundException.class})
    public ApiResponse<String> handleNotFoundException(RuntimeException ex, WebRequest webRequest, HttpServletRequest request) {
        logAndSaveError(request, webRequest, HttpStatus.NOT_FOUND, ex);
        return ApiResponse.map(null, ex.getMessage(),  HttpStatus.BAD_REQUEST);
    }


    /**
     * Handles validation errors (e.g., invalid input fields).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        log.warn("Validation Exception: {}", ex.getMessage());

        return ApiResponse.map(fieldErrors, FAIL, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all unexpected exceptions globally.
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Map<String, Object>> handleException(Exception ex, WebRequest webRequest, HttpServletRequest request) {
        HttpStatus status = getStatusFromException(ex);
        logAndSaveError(request, webRequest, status, ex);

        return ApiResponse.map(null, ex.getMessage(), status);
    }

    /**
     * Determines the HTTP status from the exception (fallback to 500 if unknown)
     */
    private HttpStatus getStatusFromException(Exception ex) {
        if (ex.getClass().isAnnotationPresent(org.springframework.web.bind.annotation.ResponseStatus.class)) {
            return ex.getClass().getAnnotation(org.springframework.web.bind.annotation.ResponseStatus.class).value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Logs and saves error details
     */
    private void logAndSaveError(HttpServletRequest request, WebRequest webRequest, HttpStatus status, Exception ex) {
        String path = webRequest.getDescription(false).replace("uri=", "");

        log.error("Exception caught: {} at {} | Method: {} | Status: {}", ex.getMessage(), path, request.getMethod(), status.value(), ex);

        logApiService.saveLog(
                request.getMethod(),
                path,
                String.valueOf(status.value()),
                0L,
                "ERROR",
                ex.getMessage()
        );
    }
}
