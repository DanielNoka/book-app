package org.springdemo.springproject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    @NotEmpty(message = "Category is required")
    @Size(max = 20, message = "Category name should not exceed 20 characters")
    private String categoryName;

    @Size(min = 20, max = 50, message = "Category description should be 30-50 characters")
    private String categoryDescription;

}
