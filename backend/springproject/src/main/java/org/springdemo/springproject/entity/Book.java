package org.springdemo.springproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @PastOrPresent(message = "Publish year must be in the past or today")
    @Column(name = "publish_year", columnDefinition = "DATE")
    private LocalDate publishYear;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<BookCategory> bookCategories = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<BookAuthor> bookAuthors = new HashSet<>();

}