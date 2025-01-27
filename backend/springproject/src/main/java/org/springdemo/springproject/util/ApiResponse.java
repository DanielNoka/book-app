package org.springdemo.springproject.util;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private T data; // Returned Data
    private String message; //  Message for succes or failture
    private HttpStatus status; //  HTTP status

    // Constructor for answer and status
    public ApiResponse(T data, HttpStatus status) {
        this.data = data;
        this.status = status;
        this.message = null;
    }

    // Constructor with message and status code
    public ApiResponse(String message, HttpStatus status) {
        this.data = null; // No data returned
        this.message = message;
        this.status = status;
    }

    public ApiResponse(HttpStatus status) {
        this.data = null; // No data returned
        this.status = status;
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
}

