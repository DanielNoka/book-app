package org.springdemo.springproject.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Book")
public class Book {
    @Id //cdo entitet duhet te kete nje identifikues unik
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement id
    @Column(name = "Id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    // Përdor LocalDate dhe specifiko që duhet të ruhet si DATE
    @Column(name = "publishYear", columnDefinition = "DATE")
    private LocalDate publishYear;
}
