package com.crashcourse.SpringBootTraining.SongService.repository;

import com.crashcourse.SpringBootTraining.SongService.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
