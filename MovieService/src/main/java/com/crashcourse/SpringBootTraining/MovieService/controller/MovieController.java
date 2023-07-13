package com.crashcourse.SpringBootTraining.MovieService.controller;

import com.crashcourse.SpringBootTraining.MovieService.dto.MovieRequestDto;
import com.crashcourse.SpringBootTraining.MovieService.dto.MovieResponseDto;
import com.crashcourse.SpringBootTraining.MovieService.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/movie/")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping(value = "/upload")
    @Operation(summary = "Upload movie", description = "Upload movie")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MovieResponseDto> uploadMovie(@RequestBody @Valid MovieRequestDto movieRequestDto) {

        return new ResponseEntity<>(movieService.uploadMovie(movieRequestDto), HttpStatus.ACCEPTED);

    }

    @GetMapping(value = "/{movieId}")
    @Operation(summary = "Get movie", description = "Get movie")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MovieResponseDto> getMovie(@PathVariable(value = "movieId") Integer movieId) {

        return new ResponseEntity<>(movieService.getMovie(movieId), HttpStatus.OK);

    }

    @PutMapping(value = "/{movieId}")
    @Operation(summary = "Like movie", description = "Like movie")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Long> likeMovie(@PathVariable(value = "movieId") Integer movieId) {

        return new ResponseEntity<>(movieService.likeMovie(movieId), HttpStatus.OK);

    }


}
