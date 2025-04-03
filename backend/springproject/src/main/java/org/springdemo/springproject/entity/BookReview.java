package org.springdemo.springproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "reviews",uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","book_id"}))//A user can do a single review per book
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookReview extends BaseEntity{

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)//When a user is deleted, their reviews will still exist but without a user reference.
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

    @Column(length = 100)
    private String comment;

    @Column(nullable = false)
    @Min(1) @Max(5)
    private int rating;

}
