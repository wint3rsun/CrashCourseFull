package com.crashcourse.SpringBootTraining.MovieService.repository;

import com.crashcourse.SpringBootTraining.MovieService.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
