package com.crashcourse.SpringBootTraining.MovieService.service;


import com.crashcourse.SpringBootTraining.MovieService.dto.ReviewRequestDto;
import com.crashcourse.SpringBootTraining.MovieService.dto.ReviewResponseDto;
import com.crashcourse.SpringBootTraining.MovieService.entity.Movie;
import com.crashcourse.SpringBootTraining.MovieService.entity.Review;
import com.crashcourse.SpringBootTraining.MovieService.util.UserUtil;
import com.crashcourse.SpringBootTraining.MovieService.repository.ReviewRepository;
import com.crashcourse.SpringBootTraining.MovieService.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MovieRepository movieRepository;

    public void reviewOnMovie(ReviewRequestDto reviewRequestDto) {
        Review review = modelMapper.map(reviewRequestDto, Review.class);
        review.setReviewedBy(UserUtil.getLoggedInUsername());
        Optional<Movie> movieOptional = movieRepository.findById(review.getMovieId().getMovieId());
        if (movieOptional.isEmpty()) {
            throw new RuntimeException("Movie doesn't exist please check");
        }
        Movie movie = movieOptional.get();
        review.setMovieId(movie);
        movie.getReviews().add(review);
        movieRepository.save(movie);
    }

    public List<ReviewResponseDto> getReviewOfMovies(Integer movieId) {
        if (null != movieId && movieRepository.existsById(movieId)) {
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getReviews().stream().map(review -> modelMapper.map(review, ReviewResponseDto.class)).collect(Collectors.toList());
        } else {
            throw new RuntimeException("Movie Doesn't exist please check movieId");
        }
    }
}
