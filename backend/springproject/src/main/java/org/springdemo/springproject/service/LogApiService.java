package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.springproject.entity.LogApi;
import org.springdemo.springproject.repository.LogApiRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogApiService {

    private final LogApiRepository logApiRepository;

    @Async // runs in seperate thread
    public void saveLog(String method, String endpoint, String responseStatus, long executionTime, String logType , String message) {

        try {
            LogApi logApi = LogApi.builder()
                    .method(method)
                    .endpoint(endpoint)
                    .responseStatus(responseStatus)
                    .executionTime(executionTime)
                    .logType(logType)
                    .message(message)
                    .timestamp(LocalDateTime.now())
                    .build();

            logApiRepository.save(logApi);
        } catch (Exception e) {
            log.error("Failed to save API log: {}", e.getMessage());
        }
    }
}
