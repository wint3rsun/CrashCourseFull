package com.crashcourse.SpringBootTraining.SongService.dto;

import com.crashcourse.SpringBootTraining.SongService.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class SongResponseDto {

    private Integer songId;

    private String songName;

    private String album;

    private String artistName;

    private Long likes;

    private List<Comment> comments;

}
