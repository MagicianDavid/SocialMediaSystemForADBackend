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

import com.example.demo.dto.PCMsgDetail;
import com.example.demo.interfacemethods.PCMsgInterface;
import com.example.demo.interfacemethods.TagInterface;
import com.example.demo.model.PCMsg;
import com.example.demo.model.Tag;
import com.example.demo.service.TaggingService;

@RestController
@RequestMapping("/api/pcmsgs")
public class PCMsgController {

	@Autowired
	private PCMsgInterface pcmsgService;
	
    @Autowired
    private TagInterface tagService;
    
	@Autowired
    private TaggingService taggingService;
    
	// return all posts 
	@GetMapping("/findAllPosts")
    public ResponseEntity<List<PCMsg>> getAllPosts() {
        List<PCMsg> postList = pcmsgService.findAllPosts();
        return ResponseEntity.ok(postList);
    }
	
	// find by UserId, it for user that log in platform.
	@GetMapping("/findAllPostsByUserId/{id}")
    public ResponseEntity<List<PCMsg>> getAllPostsByUserId(@PathVariable("id") Integer id) {
        List<PCMsg> postList = pcmsgService.findAllPostsByUserId(id);
        return ResponseEntity.ok(postList);
    }
	
	
	@GetMapping("/findPostDetailById/{id}")
    public ResponseEntity<PCMsgDetail> getPostById(@PathVariable("id") Integer id) {
		PCMsgDetail post = pcmsgService.findPCMsgDetailById(id);
        return ResponseEntity.ok(post);
    }
	
    @GetMapping("/{id}/children")
    public ResponseEntity<List<PCMsg>> getChildrenByPCMId(@PathVariable Integer id) {
        List<PCMsg> children = pcmsgService.findAllFirstLayerChildren(id);
        return ResponseEntity.ok(children);
    }
    
    @PostMapping(value = "/createPost", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PCMsg> savePost(@RequestBody PCMsg post) {
        String tags = taggingService.getTagsForText(post.getContent());
        Tag tag = new Tag();
        tag.setTag(tags);
        tag.setPCMsg(post);
        post.setTag(tag);

        PCMsg newPost = pcmsgService.savePCMsgt(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }
	
	@PostMapping(value = "/createComment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PCMsg> saveComment(@RequestBody PCMsg comment) {
		PCMsg newComment = pcmsgService.savePCMsgt(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }
}