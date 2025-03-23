package org.springdemo.springproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Method is required")
    private String method;

    @NotNull(message = "Endpoint is required")
    private String endpoint;

    @NotNull(message = "Response Status is required")
    private String responseStatus;

    @NotNull(message = "ExecutionTime is required")
    private long executionTime;

    @NotNull(message = "Log type is required")
    private String logType;

    private String message;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

}
