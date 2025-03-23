package org.springdemo.springproject.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springdemo.springproject.util.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

//Used for private endpoints
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Constants for fail message
    public static final String FAIL = "FAIL";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json");

        // Create an ErrorResponse object
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Unauthorized: You need to log in to access this resource.");
        errorResponse.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.setStatus(FAIL);
        errorResponse.setTimestamp(LocalDateTime.now());

        // Convert the ErrorResponse object to JSON and write it to the response
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule to handle LocalDateTime
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
