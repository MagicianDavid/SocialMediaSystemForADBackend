package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.interfacemethods.PostsInterface;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;

@Service
@Transactional(readOnly = true)
public class PostsService implements PostsInterface{

	@Autowired
	private PostRepository postRepository;
	
	@Override
	public List<Post> findAllPostsDateDESC() {
		return postRepository.findAllPostsOrderByDateDesc();
	}

	@Override
	public Post findPostById(Integer id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Post not found with ID:" + id));
	}

	@Override
	@Transactional(readOnly = false)
	public Post savePost(Post post) {
		// TODO Auto-generated method stub
		return postRepository.save(post);
	}

	
	
}
