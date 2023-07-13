package com.crashcourse.SpringBootTraining.MovieService.service;

import com.crashcourse.SpringBootTraining.MovieService.dto.MovieRequestDto;
import com.crashcourse.SpringBootTraining.MovieService.dto.MovieResponseDto;
import com.crashcourse.SpringBootTraining.MovieService.entity.Movie;
import com.crashcourse.SpringBootTraining.MovieService.util.UserUtil;
import com.crashcourse.SpringBootTraining.MovieService.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    ModelMapper modelMapper;

    public MovieResponseDto uploadMovie(MovieRequestDto movieRequestDto) {
        Movie movie = modelMapper.map(movieRequestDto, Movie.class);
        movie.setUploadedBy(UserUtil.getLoggedInUsername());
        return modelMapper.map(movieRepository.save(movie), MovieResponseDto.class);
    }

    public MovieResponseDto getMovie(Integer movieId) {
        if (null != movieId && movieRepository.existsById(movieId)) {
            return modelMapper.map(movieRepository.findById(movieId), MovieResponseDto.class);
        } else {
            throw new RuntimeException("Movie Doesn't exist please check movieId");
        }
    }

    public Long likeMovie(Integer movieId) {
        if (null != movieId && movieRepository.existsById(movieId)) {
            Movie movie = movieRepository.findById(movieId).get();
            Long likes = movie.getLikes() != null ? movie.getLikes() : 0L + 1L;
            movie.setLikes(likes);
            movieRepository.save(movie);
            return likes;
        } else {
            throw new RuntimeException("Movie Doesn't exist please check movieId");
        }

    }
}
