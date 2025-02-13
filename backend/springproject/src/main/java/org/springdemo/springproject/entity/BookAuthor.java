package org.springdemo.springproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book_author")
public class BookAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnore
    private Book book;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnore
    private Author author;

    public BookAuthor(Book book, Author author) {
        this.book = book;
        this.author = author;
    }
}
