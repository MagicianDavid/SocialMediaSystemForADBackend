package com.example.demo.interfacemethods;

import java.util.List;

import com.example.demo.model.Post;

public interface PostsInterface {

	List<Post> findAllPosts();
	Post findPostById (Integer id);
	
	Post savePost (Post post);
}
