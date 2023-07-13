package com.crashcourse.SpringBootTraining.MovieService.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MovieRequestDto {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, message = "name should have at least 2 characters")
    private String movieName;

    @NotEmpty(message = "Year should not be empty")
    private String year;

    private String directorName = "";

}
