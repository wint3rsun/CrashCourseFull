package com.crashcourse.SpringBootTraining.MovieService.dto;

import com.crashcourse.SpringBootTraining.MovieService.entity.Review;
import lombok.Data;

import java.util.List;

@Data
public class MovieResponseDto {

    private Integer movieId;

    private String movieName;

    private String year;

    private String directorName;

    private Long likes;

    private List<Review> reviews;

}
