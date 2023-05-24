package com.crashcourse.L8.SongService.dto;

import com.crashcourse.L8.SongService.entity.Comment;
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
