package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PCMsgDTO;
import com.example.demo.dto.PCMsgDetail;
import com.example.demo.dto.SearchResult;
import com.example.demo.interfacemethods.PCMsgInterface;
import com.example.demo.interfacemethods.TagInterface;
import com.example.demo.model.PCMsg;
import com.example.demo.model.Tag;
import com.example.demo.model.User;
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
	
	@Autowired
	@Qualifier("pagedResourcesAssembler")
    private PagedResourcesAssembler<PCMsgDTO> pagedResourcesAssembler;
    
	// return all posts 
	@GetMapping("/findAllPosts")
    public ResponseEntity<List<PCMsg>> getAllPosts() {
        List<PCMsg> postList = pcmsgService.findAllPosts();
        return ResponseEntity.ok(postList);
    }
	
	// for pagination
	@GetMapping("/findAllPostsPageable")
    public org.springframework.hateoas.PagedModel<EntityModel<PCMsgDTO>> getAllPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size) {
		Page<PCMsgDTO> pcmsgPage = pcmsgService.findAllPosts(page, size);
        return pagedResourcesAssembler.toModel(pcmsgPage);
    }
	
	// return all posts order by like & comments
	@GetMapping("/findHotPosts")
    public ResponseEntity<List<PCMsg>> getHotPosts() {
        List<PCMsg> postList = pcmsgService.findAllHotPosts();
        return ResponseEntity.ok(postList);
    }
	
	// this is for the search result page, it will return three lists
	// 1st is the all_matched_following_user_list
	// 2nd is the all_matched_user_list
	// 3rd is the all_matched_post_list(post_user or post content)
	@GetMapping("/searchBarResult/")
    public ResponseEntity<SearchResult> getSearchBarResult(@RequestParam("keyword") String k) {
        List<User> all_matched_following_user_list = pcmsgService.findAllSearchFollowingUser(k);
        List<User> all_matched_user_list = pcmsgService.findAllSearchUser(k);
        List<PCMsg> all_matched_post_list = pcmsgService.findAllSearchPostsOrderByDateDESC(k);
        
        SearchResult searchResult = new SearchResult(all_matched_following_user_list,all_matched_user_list,all_matched_post_list);
        return ResponseEntity.ok(searchResult);
    }
		
	// find by UserId, it is for user that log in platform.
	@GetMapping("/findAllPostsByUserId/{id}")
    public ResponseEntity<List<PCMsg>> getAllPostsByUserId(@PathVariable("id") Integer id) {
        List<PCMsg> postList = pcmsgService.findAllPostsByUserId(id);
        return ResponseEntity.ok(postList);
    }
	
	// find all posts for this users' following and exclude blockList -Haven't Used
	@GetMapping("/findAllFollowingPostsByUserId/{id}")
    public ResponseEntity<List<PCMsg>> getAllFollowingPostsByUserId(@PathVariable("id") Integer id) {
		//List<PCMsg> postList = pcmsgService.findAllFollowingPostsByUserId(id);
		List<PCMsg> postList = pcmsgService.findAllPostsByUserId(id);

        return ResponseEntity.ok(postList);
    }
	
	// find all posts for this users'following, !blockList, his own post
	@GetMapping("/findAllFollowingPostsAndNotDeletedByUserId/{id}")
    public ResponseEntity<List<PCMsg>> findAllFollowingPostsAndNotDeletedByUserId(@PathVariable("id") Integer id) {
		//List<PCMsg> postList = pcmsgService.findAllFollowingPostsByUserId(id);
		List<PCMsg> postList = pcmsgService.findAllFollowingPostsAndNotDeletedByUserId(id);

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
 	
 	//hasUserLikePost
 	 @GetMapping("/{postId}/likes/{userId}")
     public ResponseEntity<Boolean> hasUserLikedPost(@PathVariable int postId, @PathVariable int userId) {
         boolean isLiked = pcmsgService.hasUserLikedPost(userId, postId);
         return new ResponseEntity<>(isLiked, HttpStatus.OK);
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
		
	    String tags = taggingService.getTagsForText(comment.getContent());
        Tag tag = new Tag();
        tag.setTag(tags);
        tag.setPCMsg(comment);
        comment.setTag(tag);
	
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
	
	// update PCMsg Status  
	// need to transfer status(operation) in the request
	@PutMapping("updatePCMsgStatus/{pcmsgId}")
    public ResponseEntity<Void> updatePCMsgStatus(@PathVariable("pcmsgId")Integer pcmsgId, @RequestParam("status") String status) {
		pcmsgService.updatePCMsgStatusById(pcmsgId, status);
		return ResponseEntity.noContent().build();
    }
	
	// hide post or comment
	@PutMapping("/show/{id}")
    public ResponseEntity<Void> showPCMsg(@PathVariable("id") Integer id) {
		pcmsgService.showPCMsgById(id);
        return ResponseEntity.noContent().build();
    }
	
	// hide post or comment
	@PutMapping("/hide/{id}")
    public ResponseEntity<Void> hidePCMsg(@PathVariable("id") Integer id) {
		pcmsgService.hidePCMsgById(id);
        return ResponseEntity.noContent().build();
    }
	
	// delete post or comment
	@PutMapping("/delete/{id}")
    public ResponseEntity<Void> deletePCMsg(@PathVariable("id") Integer id) {
		pcmsgService.deletePCMsgById(id);
        return ResponseEntity.noContent().build();
    }
}