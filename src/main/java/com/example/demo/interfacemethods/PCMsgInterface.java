package com.example.demo.interfacemethods;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.model.PCMsg;
import com.example.demo.model.User;
import com.example.demo.dto.PCMsgDTO;
import com.example.demo.dto.PCMsgDetail;

public interface PCMsgInterface {

	List<PCMsg> findAllPCMsgDateDESC();
	// return whatever posts in our database
	List<PCMsg> findAllPosts();
	Page<PCMsgDTO> findAllPosts(Integer page, Integer size);
	// return all posts of the specific user with userId
	List<PCMsg> findAllPostsByUserId(Integer userId);
	// return this user's following users' posts
	List<PCMsg> findAllFollowingPostsByUserId(Integer userId);

	// return all FollowingPost, Not Deleted, Include own Post
	List<PCMsg> findAllFollowingPostsAndNotDeletedByUserId(Integer userId);
	// return hot posts in our database
	// according to the amount of comments and likes
	List<PCMsg> findAllHotPosts();
	
	 
	
	// the next three interface/service are for the search bar use case
	// return related search following users by user_name
	List<User> findAllSearchFollowingUser(String keyword);
	// return related search users by user_name
	List<User> findAllSearchUser(String keyword);
	// return related search posts by post content or post user_name
	List<PCMsg> findAllSearchPostsOrderByDateDESC(String keyword);
	
	// return all children(just the first layer) with fatherId/its own id
	List<PCMsg> findAllFirstLayerChildren(Integer fatherId);
	PCMsg findPCMsgById (Integer id);
	// add/delete like to post or comment by its id
	void updateLikeForPCMsgByIds(Integer UserId, Integer PCMsgId);
	// Find post or comment by its id and count its total likes
	Integer countLikesByPCMsgId(Integer id);
	// Count Comments by PostId ,take comments_comments into count
	Integer countTotalCommentsByPostId(Integer postId);
	// Return Post & its first layer comments by postId
	PCMsgDetail findPCMsgDetailById (Integer postId);
	
	// Return If User like the post
	boolean hasUserLikedPost(int userId, int postId);

	// CRUD
	PCMsg savePCMsgt (PCMsg pcmsg);
	PCMsg updatePCMsg (Integer id, PCMsg pcmsg);
	void updatePCMsgStatusById(Integer id,String status);
	void showPCMsgById(Integer id);
	void deletePCMsgById(Integer id);
	void hidePCMsgById(Integer id);
}