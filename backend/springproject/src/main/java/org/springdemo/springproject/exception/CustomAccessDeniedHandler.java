package org.springdemo.springproject.exception;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springdemo.springproject.util.ErrorResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springdemo.springproject.util.Constants.FAIL;

//Used for authorization when a user want to access delete endpoints
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        // Create an ErrorResponse object
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("You do not have permission to access this resource.");
        errorResponse.setStatusCode(HttpServletResponse.SC_FORBIDDEN);
       errorResponse.setStatus(FAIL);
        errorResponse.setTimestamp(LocalDateTime.now());

        // Convert the map to JSON and write it to the response
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule to handle LocalDateTime
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
