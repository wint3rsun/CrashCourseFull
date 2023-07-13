package com.crashcourse.SpringBootTraining.MovieService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "movie")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    private String movieName;

    private String year;

    private String directorName;

    private Long likes;

    private String uploadedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movieId")
    private List<Review> reviews;


}
