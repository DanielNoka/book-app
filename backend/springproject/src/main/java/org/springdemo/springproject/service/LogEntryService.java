package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springdemo.springproject.entity.LogEntry;
import org.springdemo.springproject.repository.LogEntryRepository;

import java.io.PrintWriter;
import java.io.StringWriter;

@Service
@RequiredArgsConstructor
public class LogEntryService {

    private final LogEntryRepository logEntryRepository;

    @Async // Makes it non-blocking
    public void saveErrorLog(String errorMessage, String methodName) {

        LogEntry logEntry = new LogEntry(errorMessage, methodName);
        logEntryRepository.save(logEntry);
    }
}
