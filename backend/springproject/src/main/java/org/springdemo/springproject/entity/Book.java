package org.springdemo.springproject.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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


    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();


}
