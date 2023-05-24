package com.crashcourse.L8.SongService.controller;

import com.crashcourse.L8.SongService.dto.SongRequestDto;
import com.crashcourse.L8.SongService.dto.SongResponseDto;
import com.crashcourse.L8.SongService.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/song/")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping(value = "/upload")
    @Operation(summary = "Upload song", description = "Upload song")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<SongResponseDto>  uploadSong(@RequestBody @Valid SongRequestDto songRequestDto) {

        return new ResponseEntity<>(songService.uploadSong(songRequestDto), HttpStatus.ACCEPTED);

    }

    @GetMapping(value = "/{songId}")
    @Operation(summary = "Get song", description = "Get song")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<SongResponseDto> getSong(@PathVariable(value = "songId") Integer songId) {

        return new ResponseEntity<>(songService.getSong(songId), HttpStatus.OK);

    }

    @PutMapping(value = "/{songId}")
    @Operation(summary = "Like song", description = "Like song")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Long> likeSong(@PathVariable(value = "songId") Integer songId) {

        return new ResponseEntity<>(songService.likeSong(songId), HttpStatus.OK);

    }


}
