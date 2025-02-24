package org.springdemo.springproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateAuthorDTO {

    @NotEmpty(message = "Name can't be empty")
    private String name;

    @NotEmpty(message = "Name can't be empty")
    private String surname;

    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Nationality cannot be empty")
    private String nationality;

    @Size(max = 50, message = "Nationality should not exceed 50 characters")
    private String description;


}
