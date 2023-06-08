package com.crashcourse.SpringBootTraining.SongService.dto;


import lombok.Data;

@Data
public class CommentResponse {

    private String comment;

    private String commentedBy;
}
