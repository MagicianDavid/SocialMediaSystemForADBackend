package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.interfacemethods.CommentInterface;
import com.example.demo.interfacemethods.PostsInterface;
import com.example.demo.interfacemethods.TagInterface;
import com.example.demo.model.Comments;
import com.example.demo.model.Post;
import com.example.demo.model.Tag;
import com.example.demo.service.TaggingService;

@RestController
@RequestMapping("/api/posts")
public class UsersController {

	@Autowired
	private PostsInterface postService;
    @Autowired
    private TagInterface tagService;
	@Autowired
	private CommentInterface commentService;
    
    
	@Autowired
    private TaggingService taggingService;
	
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> savePost(@RequestBody Post post) {
        String tags = taggingService.getTagsForText(post.getContent());
        Tag tag = new Tag();
        tag.setTag(tags);
        tag.setPost(post);
        post.setTag(tag);

        Post newPost = postService.savePost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }
	
    
	//need one where find by UserId, it for user that log in platform.
	@GetMapping("/findAll")
    public ResponseEntity<List<Post>> getAllEmployee() {
        List<Post> postList = postService.findAllPostsDateDESC();
        return ResponseEntity.ok(postList);
    }
	
	@GetMapping("/findById/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Integer id) {
        Post post = postService.findPostById(id);
        return ResponseEntity.ok(post);
    }
	
	@PostMapping(value = "/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comments> saveComment(@RequestBody Comments comment) {
        Comments newComment = commentService.saveComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }
	
	
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comments>> getCommentsByPostId(@PathVariable int id) {
        List<Comments> comments = commentService.getCommentsByPostId(id);
        return ResponseEntity.ok(comments);
    }
}
