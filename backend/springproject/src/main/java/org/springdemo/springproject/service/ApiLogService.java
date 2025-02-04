package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.entity.ApiLog;
import org.springdemo.springproject.repository.ApiLogRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApiLogService {

    private final ApiLogRepository apiLogRepository;

    public void saveLog(String method, String endpoint, String requestBody, String responseStatus, long executionTime) {
        ApiLog log = ApiLog.builder()
                .method(method)
                .endpoint(endpoint)
                .requestBody(requestBody)
                .responseStatus(responseStatus)
                .executionTime(executionTime)
                .timestamp(LocalDateTime.now())
                .build();

        apiLogRepository.save(log);
    }
}
