package org.springdemo.springproject.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {
    private final T data; // Returned Data
    private final String message; //  Message for succes or failture
    private final HttpStatus status; //  HTTP status

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss")
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

