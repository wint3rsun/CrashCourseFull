package com.crashcourse.SpringBootTraining.SongService.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    private String comment;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "songId")
    private Song songId;

    private String commentedBy;
}
