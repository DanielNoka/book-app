package org.springdemo.springproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //REMOVE TO MANY .SET() CALLS FROM CLASS OBJECT
@Table(name = "log_api")
public class LogApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;
    private String endpoint;
    private String responseStatus;
    private long executionTime;
    private String logType;
    private String message; //Store error message if exception happened

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}
