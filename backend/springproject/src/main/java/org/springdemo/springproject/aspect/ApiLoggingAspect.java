package org.springdemo.springproject.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springdemo.springproject.service.LogApiService;
import org.springdemo.springproject.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class ApiLoggingAspect {

    private final LogApiService logApiService;

    @Around("execution(* org.springdemo.springproject.controller.*.*(..))") // Logs only controller methods
    public Object logApiCall(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        HttpServletRequest request = getRequest();
        String logType = "INFO";
        Object result = joinPoint.proceed();
        String responseStatus = extractResponseStatus(result);

        logApiService.saveLog(
                request.getMethod(),
                request.getRequestURI(),
                responseStatus,
                System.currentTimeMillis() - startTime,
                logType,
                null);

        return result;
    }

   /**
   * obtain the HttpServletRequest in a thread-safe way
   */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private String extractResponseStatus(Object result) {
        if (result instanceof ApiResponse<?> apiResponse) {
            return apiResponse.getStatus().toString();
        }
        return "UNKNOWN";
    }

}