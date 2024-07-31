package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	// find all posts for this users' following and exclude blockList
	@GetMapping("/findAllFollowingPostsByUserId/{id}")
    public ResponseEntity<List<PCMsg>> getAllFollowingPostsByUserId(@PathVariable("id") Integer id) {
		List<PCMsg> postList = pcmsgService.findAllFollowingPostsByUserId(id);
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
    
    // countTotalCommentsByPostId including comment_comment
 	@GetMapping("/countAllCommentsByPostId/{postId}")
     public ResponseEntity<Integer> getTotalCommentsByPostId(@PathVariable("postId") Integer postId) {
         Integer totalComments = pcmsgService.countTotalCommentsByPostId(postId);
         return ResponseEntity.ok(totalComments);
     }
 	
 	// countLikesByPCMsgId all for this post/comment
 	@GetMapping("/countLikesByPCMsgId/{pcmsgId}")
    public ResponseEntity<Integer> getLikesByPCMsgId(@PathVariable("pcmsgId") Integer pcmsgId) {
        Integer totalLikes = pcmsgService.countLikesByPCMsgId(pcmsgId);
        return ResponseEntity.ok(totalLikes);
    }
    
 	// TODO: post sourceId should be null
 	// and the tag logic haven't done
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
	
    // TODO: comment sourceId should be its upper layer id 
    // and the tag logic haven't done
	@PostMapping(value = "/createComment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PCMsg> saveComment(@RequestBody PCMsg comment) {
		PCMsg newComment = pcmsgService.savePCMsgt(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }
	
	// can save new like also can delete existing like(unlike)
	@PutMapping("likeOrUnlikePCMsg/{userId}/{pcmsgId}")
    public ResponseEntity<Void> LikeOrUnlikePCMsg(@PathVariable("userId") Integer userId, @PathVariable("pcmsgId")Integer pcmsgId) {
		pcmsgService.updateLikeForPCMsgByIds(userId, pcmsgId);
		return ResponseEntity.noContent().build();
    }
	
	// update PCMsg
	@PutMapping("updatePCMsg/{pcmsgId}")
    public ResponseEntity<PCMsg> updatePCMsg(@PathVariable("pcmsgId")Integer pcmsgId, @RequestBody PCMsg newPCMsg) {
		PCMsg updatedPCMsg = pcmsgService.updatePCMsg(pcmsgId, newPCMsg);
		return ResponseEntity.status(HttpStatus.OK).body(updatedPCMsg);
    }
	
	// update pcmsgStatus 
	@PutMapping("updatePCMsgStatus/{pcmsgId}")
    public ResponseEntity<Void> updatePCMsgStatus(@PathVariable("pcmsgId")Integer pcmsgId, @RequestParam("status") String status) {
		pcmsgService.updatePCMsgStatusById(pcmsgId, status);
		return ResponseEntity.noContent().build();
    }
	
	// delete post or comment and all its children
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePCMsg(@PathVariable("id") Integer id) {
		pcmsgService.deletePCMsgById(id);
        return ResponseEntity.noContent().build();
    }
}