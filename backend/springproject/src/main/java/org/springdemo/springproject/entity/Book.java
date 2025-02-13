package org.springdemo.springproject.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.*;



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


    // Përdor LocalDate dhe specifiko që duhet të ruhet si DATE
    @Column(name = "publishYear", columnDefinition = "DATE")
    private LocalDate publishYear;


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<BookAuthor> bookAuthors = new HashSet<>();

}