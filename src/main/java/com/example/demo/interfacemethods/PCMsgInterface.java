package com.example.demo.interfacemethods;

import java.util.List;

import com.example.demo.model.PCMsg;
import com.example.demo.dto.PCMsgDetail;

public interface PCMsgInterface {

	List<PCMsg> findAllPCMsgDateDESC();
	// return whatever posts in our database
	List<PCMsg> findAllPosts();
	// return all posts of the specific user with userId
	List<PCMsg> findAllPostsByUserId(Integer userId);
	// return this user's following users' posts
	List<PCMsg> findAllFollowingPostsByUserId(Integer userId);
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
	
	// CRUD
	PCMsg savePCMsgt (PCMsg pcmsg);
	PCMsg updatePCMsg (Integer id, PCMsg pcmsg);
	void updatePCMsgStatusById(Integer id,String status);
	void deletePCMsgById(Integer id);
}