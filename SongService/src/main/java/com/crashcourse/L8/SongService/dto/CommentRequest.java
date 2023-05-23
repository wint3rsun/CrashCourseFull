package com.crashcourse.L8.SongService.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {

    @NotEmpty(message = "comment should not be empty")
    @Size(min = 2, message = "comment should have at least 2 characters")
    private String comment;

    private Integer songId;
}
