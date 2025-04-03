package org.springdemo.springproject.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    @NotEmpty(message = "Title is required")
    @Size(max = 50, message = "Title should not exceed 50 characters")
    private String title;

    @PastOrPresent(message = "Publish date must be in the past or present")
    private LocalDate publishYear;

}
