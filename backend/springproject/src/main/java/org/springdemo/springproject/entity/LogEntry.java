package org.springdemo.springproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "log_entries")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "error_message", columnDefinition = "TEXT") // Store long error messages
    private String errorMessage;

    @Column(name = "method_name")
    private String methodName;


    @Column(name = "timestamp")
    private LocalDateTime time;

    public LogEntry(String errorMessage, String methodName) {
        this.errorMessage = errorMessage;
        this.methodName = methodName;
        this.time = LocalDateTime.now();
    }
}
