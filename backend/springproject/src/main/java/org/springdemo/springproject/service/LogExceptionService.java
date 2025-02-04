package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springdemo.springproject.entity.LogException;
import org.springdemo.springproject.repository.LogEntryRepository;

@Service
@RequiredArgsConstructor
public class LogExceptionService {

    private final LogEntryRepository logEntryRepository;

    @Async // Makes it non-blocking
    public void saveErrorLog(String errorMessage, String methodName) {

        LogException logException = new LogException(errorMessage, methodName);
        logEntryRepository.save(logException);
    }
}
