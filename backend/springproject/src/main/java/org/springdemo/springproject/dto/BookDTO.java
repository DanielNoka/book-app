package org.springdemo.springproject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookDTO {

    @NotEmpty(message = "Title is required")
    @Size(max = 20, message = "Title should not exceed 20 characters")
    private String title;

    @PastOrPresent(message = "Publish date must be in the past or present")
    private LocalDate publishYear;

}
