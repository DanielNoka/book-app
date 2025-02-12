package org.springdemo.springproject.dto;


import java.time.LocalDate;
import java.util.List;
import lombok.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private long id;

    @NotEmpty(message = "Title cannot be empty")
    @Size(max = 255, message = "Title should not exceed 255 characters")
    private String title;

    private LocalDate publishYear;
    private List<Long> authorIds;



}

