package org.springdemo.springproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;        // GET, POST, etc.
    private String endpoint;      // API path
    private String requestBody;   // Request data
    private String responseStatus;// HTTP status
    private long executionTime;   // Time taken in ms

    private LocalDateTime timestamp;
}
