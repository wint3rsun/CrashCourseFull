package com.crashcourse.SpringBootTraining.SongService.service;


import com.crashcourse.SpringBootTraining.SongService.dto.CommentRequest;
import com.crashcourse.SpringBootTraining.SongService.dto.CommentResponse;
import com.crashcourse.SpringBootTraining.SongService.util.UserUtil;
import com.crashcourse.SpringBootTraining.SongService.entity.Comment;
import com.crashcourse.SpringBootTraining.SongService.entity.Song;
import com.crashcourse.SpringBootTraining.SongService.repository.CommentRepository;
import com.crashcourse.SpringBootTraining.SongService.repository.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    SongRepository songRepository;

    public void commentOnSong(CommentRequest commentRequest) {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        comment.setCommentedBy(UserUtil.getLoggedInUsername());
        Optional<Song> songOptional = songRepository.findById(comment.getSongId().getSongId());
        if (songOptional.isEmpty()) {
            throw new RuntimeException("Song doesn't exist please check");
        }
        Song song = songOptional.get();
        comment.setSongId(song);
        song.getComments().add(comment);
        songRepository.save(song);
    }

    public List<CommentResponse> getCommentOfSongs(Integer songId) {
        if (null != songId && songRepository.existsById(songId)) {
            Song song = songRepository.findById(songId).get();
            return song.getComments().stream().map(comment -> modelMapper.map(comment, CommentResponse.class)).collect(Collectors.toList());
        } else {
            throw new RuntimeException("Song Doesn't exist please check songId");
        }
    }
}
