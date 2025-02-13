package org.springdemo.springproject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateBookDTO {

    @NotEmpty(message = "Title cannot be empty")
    @Size(max = 20, message = "Title should not exceed 20 characters")
    private String title;

    private LocalDate publishYear;
}
