package org.springdemo.springproject.util;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private T data; // Returned Data
    private String message; //  Message for succes or failture
    private HttpStatus status; //  HTTP status
    private LocalDateTime timestamp;

    public ApiResponse(T data, String message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }


    public static <T> ApiResponse<T> of(T data, String message, HttpStatus status) {
        return new ApiResponse<>(data, message, status);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

