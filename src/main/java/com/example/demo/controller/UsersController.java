package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.interfacemethods.PostsInterface;
import com.example.demo.model.Post;

@RestController
@RequestMapping("/api/posts")
public class UsersController {

	@Autowired
	private PostsInterface postService;
	
    @PostMapping("/create")
    public ResponseEntity<Post> saveRole(@RequestBody Post post) {
    	Post newpost = postService.savePost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(newpost);
    }
	
	//need one where find by UserId, it for user that log in platform.
	@GetMapping("/findAll")
    public ResponseEntity<List<Post>> getAllEmployee() {
        List<Post> postList = postService.findAllPosts();
        return ResponseEntity.ok(postList);
    }
	
	@GetMapping("/findByyId/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Integer id) {
        Post post = postService.findPostById(id);
        return ResponseEntity.ok(post);
    }
	
	

	
}
