package org.springdemo.springproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    private Long id;
    private String name;
    private String email;
    private String nationality;
    private String description;

    private List<Long> bookIds;

}