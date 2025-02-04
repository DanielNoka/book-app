package org.springdemo.springproject.dto;


import java.time.LocalDate;
import lombok.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class BookDto {

    @NotEmpty(message = "Title cannot be empty")
    @Size(max = 255, message = "Title should not exceed 255 characters")
    private String title;

    @NotEmpty(message = "Author cannot be empty")
    @Size(max = 255, message = "Author should not exceed 255 characters")

    private String author;
    private LocalDate publishYear;
}

