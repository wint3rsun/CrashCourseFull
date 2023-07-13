package com.crashcourse.SpringBootTraining.MovieService.controller;

import com.crashcourse.SpringBootTraining.MovieService.dto.ReviewRequestDto;
import com.crashcourse.SpringBootTraining.MovieService.dto.ReviewResponseDto;
import com.crashcourse.SpringBootTraining.MovieService.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "rest/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;


    @PostMapping(value = "/Add")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Review movie", description = "Review movie")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> reviewMovie(@RequestBody @Valid ReviewRequestDto reviewRequestDto) {
        reviewService.reviewOnMovie(reviewRequestDto);
        return new ResponseEntity<>("Reviewed Successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/{movieId}")
    @Operation(summary = "Get Reviews movie", description = "Get reviews movie")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsOfMovie(@PathVariable(value = "movieId") Integer movieId) {

        return new ResponseEntity<>(reviewService.getReviewOfMovies(movieId), HttpStatus.OK);
    }

}
