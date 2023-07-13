package com.crashcourse.SpringBootTraining.MovieService.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "review")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    private String review;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "movieId")
    private Movie movieId;

    private String reviewedBy;
}
