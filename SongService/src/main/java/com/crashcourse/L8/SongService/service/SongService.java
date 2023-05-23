package com.crashcourse.L8.SongService.service;

import com.crashcourse.L8.SongService.dto.SongRequestDto;
import com.crashcourse.L8.SongService.dto.SongResponseDto;
import com.crashcourse.L8.SongService.util.UserUtil;
import com.crashcourse.L8.SongService.entity.Song;
import com.crashcourse.L8.SongService.repository.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;
    @Autowired
    ModelMapper modelMapper;
    public SongResponseDto uploadSong(SongRequestDto songRequestDto) {
        Song song = modelMapper.map(songRequestDto, Song.class);
        song.setUploadedBy(UserUtil.getLoggedInUsername());
        return modelMapper.map(songRepository.save(song), SongResponseDto.class);
    }

    public SongResponseDto getSong(Integer songId) {
        if (null != songId && songRepository.existsById(songId)) {
            return modelMapper.map(songRepository.findById(songId), SongResponseDto.class);
        } else {
            throw new RuntimeException("Song Doesn't exist please check songId");
        }
    }

    public Long likeSong(Integer songId) {
        if (null != songId && songRepository.existsById(songId)) {
            Song song = songRepository.findById(songId).get();
            Long likes = song.getLikes() != null ? song.getLikes() : 0L + 1L;
            song.setLikes(likes);
            songRepository.save(song);
            return likes;
        } else {
            throw new RuntimeException("Song Doesn't exist please check songId");
        }

    }
}
