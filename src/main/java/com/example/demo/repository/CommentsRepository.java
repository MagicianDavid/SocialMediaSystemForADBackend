package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Comments;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {
    
	@Query("SELECT c FROM Comments c WHERE c.post.id = :postId")
    List<Comments> findByPostId(@Param("postId") int postId);
}
