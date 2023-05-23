package com.crashcourse.L8.SongService.dto;


import lombok.Data;

@Data
public class CommentResponse {

    private String comment;

    private String commentedBy;
}
