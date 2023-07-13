package com.crashcourse.SpringBootTraining.MovieService.dto;


import lombok.Data;

@Data
public class ReviewResponseDto {

    private String review;

    private String reviewedBy;
}
