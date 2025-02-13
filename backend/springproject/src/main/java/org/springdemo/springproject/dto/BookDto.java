package org.springdemo.springproject.dto;


import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private long id;
    private String title;
    private LocalDate publishYear;

    private List<Long> authorIds;

}

