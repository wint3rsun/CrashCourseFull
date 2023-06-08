package com.crashcourse.SpringBootTraining.SongService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "song")
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer songId;

    private String songName;

    private String album;

    private String artistName;

    private Long likes;

    private String uploadedBy;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "songId")
    private List<Comment> comments;


}
