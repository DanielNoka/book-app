package org.springdemo.springproject.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category extends BaseEntity{

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "category_description", nullable = false)
    private String categoryDescription;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<BookCategory> bookCategories = new HashSet<>();

}
