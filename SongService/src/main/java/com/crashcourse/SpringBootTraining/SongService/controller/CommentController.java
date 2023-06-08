package com.crashcourse.SpringBootTraining.SongService.controller;

import com.crashcourse.SpringBootTraining.SongService.dto.CommentRequest;
import com.crashcourse.SpringBootTraining.SongService.dto.CommentResponse;
import com.crashcourse.SpringBootTraining.SongService.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "rest/comment")
public class CommentController {

    @Autowired
    CommentService commentService;


    @PostMapping(value = "/Add")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Comment song", description = "Comment song")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> commentSong(@RequestBody @Valid CommentRequest commentRequest){
        commentService.commentOnSong(commentRequest);
        return new ResponseEntity<>("Commented Successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/{songId}")
    @Operation(summary = "Get Comments song", description = "Get comments song")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<CommentResponse>> getCommentsOfSong(@PathVariable(value = "songId") Integer songId){

        return new ResponseEntity<>(commentService.getCommentOfSongs(songId),HttpStatus.OK);
    }

}
