package com.crashcourse.L8.SongService.repository;

import com.crashcourse.L8.SongService.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
