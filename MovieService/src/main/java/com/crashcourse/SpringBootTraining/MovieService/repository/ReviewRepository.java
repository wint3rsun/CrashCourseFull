package com.crashcourse.SpringBootTraining.MovieService.repository;

import com.crashcourse.SpringBootTraining.MovieService.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
