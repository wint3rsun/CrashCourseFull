package com.crashcourse.SpringBootTraining.MovieService.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewRequestDto {

    @NotEmpty(message = "review should not be empty")
    @Size(min = 2, message = "review should have at least 2 characters")
    private String review;

    private Integer movieId;
}
