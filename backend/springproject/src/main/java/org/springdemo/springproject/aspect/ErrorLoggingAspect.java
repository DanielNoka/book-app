package org.springdemo.springproject.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springdemo.springproject.service.LogEntryService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class ErrorLoggingAspect {

    private final LogEntryService logEntryService;

    @Around("execution(* org.springdemo.springproject.service.*.*(..))") // Capture all service methods
    public Object logError(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            String methodName = joinPoint.getSignature().getName();
            logEntryService.saveErrorLog(e.getMessage(), methodName);
            throw e; // Rethrow so the app doesn't ignore the error
        }
    }
}

