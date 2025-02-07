package org.springdemo.springproject.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springdemo.springproject.service.LogApiService;
import org.springdemo.springproject.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;


@Aspect
@Component
@RequiredArgsConstructor
public class ApiLoggingAspect {

    private final LogApiService logApiService;
    private final HttpServletRequest request;

    @Around("execution(* org.springdemo.springproject.controller.*.*(..))") // Logs only controller methods
    public Object logApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String responseStatus;
        String logType = "INFO";

        try {
            Object result = joinPoint.proceed();

            if (result instanceof ApiResponse<?> apiResponse) {
                responseStatus = apiResponse.getStatus().toString();
            } else {
                responseStatus = "UNKNOWN";
            }

            logRequest(responseStatus, startTime, logType, null);
            return result;
        } catch (Exception e) {
            throw e; // Rethrow the exception
        }
    }


    private void logRequest(String status, long startTime, String logType,String message) {
        logApiService.saveLog(
                request.getMethod(),
                request.getRequestURI(),
                status,
                System.currentTimeMillis() - startTime,
                logType,
                message
        );
    }
}
