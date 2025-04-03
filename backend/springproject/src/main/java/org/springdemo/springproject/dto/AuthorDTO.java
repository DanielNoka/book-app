package org.springdemo.springproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Surname is required")
    private String surname;

    @NotEmpty(message = "Email is required" )
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Nationality is required")
    private String nationality;

    @Size(max = 50, message = "Description should not exceed 50 characters")
    private String description;

}
