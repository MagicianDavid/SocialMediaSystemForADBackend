package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.interfacemethods.CommentInterface;
import com.example.demo.model.Comments;
import com.example.demo.model.Post;
import com.example.demo.repository.CommentsRepository;
import com.example.demo.repository.PostRepository;

@Service
@Transactional(readOnly = true)
public class CommentsService implements CommentInterface{

	@Autowired
	private CommentsRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;

	
	@Override
	@Transactional(readOnly = false)
	public Comments saveComment(Comments comment) {
		Post post = postRepository.findById(comment.getPost().getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        comment.setPost(post);
        return commentRepository.save(comment);
	}

	@Override
	public List<Comments> getCommentsByPostId(int postId) {
        return commentRepository.findByPostId(postId);
    }

	
}
