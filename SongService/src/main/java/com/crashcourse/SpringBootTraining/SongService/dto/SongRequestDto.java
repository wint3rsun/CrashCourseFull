package com.crashcourse.SpringBootTraining.SongService.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SongRequestDto {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, message = "name should have at least 2 characters")
    private String songName;

    @NotEmpty(message = "album should not be empty")
    private String album;

    private String artistName="";

}
