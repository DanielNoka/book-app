package org.springdemo.springproject.dto;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
public class BookUpdateDto {
    private Long id;
    private String title;
    private String author;
    private LocalDate publishYear;

}

