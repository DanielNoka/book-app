package org.springdemo.springproject.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
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

    //Remove the use of "new" keyword
    public static <T> ApiResponse<T> map (T data, String message, HttpStatus status) {
        return new ApiResponse<>(data, message, status);
    }


}

