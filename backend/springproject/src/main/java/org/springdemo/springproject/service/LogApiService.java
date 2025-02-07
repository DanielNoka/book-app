package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.entity.LogApi;
import org.springdemo.springproject.repository.LogApiRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogApiService {

    private final LogApiRepository logApiRepository;

    public void saveLog(String method, String endpoint, String responseStatus, long executionTime, String logType , String message) {
        LogApi log = LogApi.builder()
                .method(method)
                .endpoint(endpoint)
                .responseStatus(responseStatus)
                .executionTime(executionTime)
                .logType(logType)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        logApiRepository.save(log);
    }
}
