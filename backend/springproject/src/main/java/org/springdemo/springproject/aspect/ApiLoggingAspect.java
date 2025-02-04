package org.springdemo.springproject.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springdemo.springproject.service.ApiLogService;
import org.springdemo.springproject.service.LogExceptionService;
import org.springdemo.springproject.util.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class ApiLoggingAspect {

    private final ApiLogService apiLogService;
    private final HttpServletRequest request;
    private final ObjectMapper objectMapper; //Converts Java objects to JSON (used for logging request bodies).
    private final LogExceptionService logExceptionService;

    @Around("execution(* org.springdemo.springproject.controller.*.*(..))") // Intercepts all controllers
    public Object logApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String responseStatus = "UNKNOWN";

        try {
            Object result = joinPoint.proceed();

            if (result instanceof ApiResponse<?> apiResponse) {
                responseStatus = apiResponse.getStatus().toString();
            }

            logRequest(responseStatus, startTime, joinPoint.getArgs());
            return result;
        } catch (Exception e) {
            String methodName = joinPoint.getSignature().getName();
            logExceptionService.saveErrorLog(e.getMessage(), methodName);

            logRequest("500 INTERNAL_SERVER_ERROR", startTime, joinPoint.getArgs());
            throw e;
        }
    }

    private void logRequest(String status, long startTime, Object[] args) {
        apiLogService.saveLog(
                request.getMethod(),
                request.getRequestURI(),
                extractRequestBody(args), // Pass args to extractRequestBody
                status,
                System.currentTimeMillis() - startTime
        );
    }

    //Converts the request body (method parameters) into JSON
    private String extractRequestBody(Object[] args) {
        try {
            return objectMapper.writeValueAsString(
                    Arrays.stream(args)
                            .filter(arg -> !(arg instanceof HttpServletRequest)) // Avoid logging request object itself
                            .toArray()
            );
        } catch (Exception e) {
            return "Could not parse request body";
        }
    }
}
