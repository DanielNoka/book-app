package org.springdemo.springproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "author", schema = "public")
public class Author extends BaseEntity {

    @Column(name = "name", nullable = false)
    @JsonIgnore
    private String name;

    @Column(name = "surname", nullable = false)
    @JsonIgnore
    private String surname;

    @Transient
    public String getFullName() {
        return this.name + " " + this.surname;
    }

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "nationality", nullable = false, length = 50)
    private String nationality;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<BookAuthor> bookAuthors = new TreeSet<>(); //maintain insertion order

}