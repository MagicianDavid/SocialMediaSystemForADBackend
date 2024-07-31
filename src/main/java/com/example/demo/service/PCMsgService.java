package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.PCMsgDetail;
import com.example.demo.interfacemethods.PCMsgInterface;
import com.example.demo.model.PCMsg;
import com.example.demo.model.User;
import com.example.demo.model.Following;
import com.example.demo.model.Like;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PCMsgRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class PCMsgService implements PCMsgInterface{

	@Autowired
	private PCMsgRepository pcmsgRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LikeRepository likeRepository;
	
	private void changePCMsgStatusById(Integer id, String operation) {
		try {
			PCMsg curPCMsg = findPCMsgById(id);
			curPCMsg.setStatus(operation);
			pcmsgRepository.save(curPCMsg);
		} catch (Exception e) {
			throw new RuntimeException("Failed to " + operation + " post/comment with ID: " + id, e);
		}
	}
	
	// count all Comments
	private int countCommentsRecursively(Integer pcmsgId) {
        List<PCMsg> children = pcmsgRepository.findAllFirstLayerChildren(pcmsgId);
        int count = children.size();
        for (PCMsg child : children) {
            count += countCommentsRecursively(child.getId());
        }
        return count;
    }
	
	// delete itself and its children using recursion
	private void deletePCMsgAndChildren(Integer pcmsgId) {
        List<PCMsg> children = pcmsgRepository.findAllFirstLayerChildren(pcmsgId);
        for (PCMsg child : children) {
            deletePCMsgAndChildren(child.getId());
        }
        pcmsgRepository.deleteById(pcmsgId);
    }
	
	@Override
	public List<PCMsg> findAllPCMsgDateDESC() {
		return pcmsgRepository.findAllPCMsgsOrderByDateDesc();
	}
	
	@Override
	public List<PCMsg> findAllPostsByUserId(Integer userId) {
		return pcmsgRepository.findAllPostsByUserIdByDateDesc(userId);
	}
	
	@Override
	public List<PCMsg> findAllFollowingPostsByUserId(Integer userId) {
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID:" + userId));
		List<Following> followings = user.getFollowings();
		String blockList = user.getBlockList();

        List<Integer> followingUserIds = followings.stream()
                .map(following -> following.getFollowingUser().getId())
                .collect(Collectors.toList());

        List<Integer> blockedUserIds = Arrays.stream(blockList.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return pcmsgRepository.findAllFollowingPostsByUserId(followingUserIds, blockedUserIds);
	}
	
	@Override
	public List<PCMsg> findAllPosts() {
		return pcmsgRepository.findAll();
	}

	@Override
	public List<PCMsg> findAllFirstLayerChildren(Integer fatherId) {
		// make sure this post/comment exists
		findPCMsgById(fatherId);
		return pcmsgRepository.findAllFirstLayerChildren(fatherId);
	}

	@Override
	public PCMsg findPCMsgById(Integer id) {
		return pcmsgRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Post/Comment not found with ID:" + id));
	}

	@Override
	@Transactional(readOnly = false)
	public void updateLikeForPCMsgByIds(Integer userId, Integer pcmsgId) {
		Like curLike = likeRepository.hasWithUserAndPCMsg(userId,pcmsgId);
		if ( curLike != null) {
			// unlike
			likeRepository.delete(curLike);
		} else {
			// save
			Like newLike = new Like(userId,pcmsgId);
			likeRepository.save(newLike);
		}
	}

	@Override
	public Integer countLikesByPCMsgId(Integer id) {
		return likeRepository.countLikesByPCMsgId(id);
	}

	@Override
	public Integer countTotalCommentsByPostId(Integer postId) {
		// Make sure this post exists
        findPCMsgById(postId);
        // Recursively count comments
        return countCommentsRecursively(postId);
	}
	
	@Override
	public PCMsgDetail findPCMsgDetailById(Integer postId) {
		PCMsg findPost = findPCMsgById(postId);
		List<PCMsg> findComments = findAllFirstLayerChildren(postId);
		return new PCMsgDetail(findPost,findComments);
	}

	@Override
	@Transactional(readOnly = false)
	public PCMsg savePCMsgt(PCMsg pcmsg) {
		return pcmsgRepository.save(pcmsg);
	}

	@Override
	@Transactional(readOnly = false)
	public PCMsg updatePCMsg(Integer id, PCMsg pcmsg) {
		PCMsg curPCMsg = findPCMsgById(id);
	    
	    curPCMsg.setImageUrl(pcmsg.getImageUrl());
	    curPCMsg.setContent(pcmsg.getContent());
	    curPCMsg.setSourceId(pcmsg.getSourceId());
	    curPCMsg.setTimeStamp(pcmsg.getTimeStamp());
	    curPCMsg.setVisibility(pcmsg.getVisibility());
	    curPCMsg.setStatus(pcmsg.getStatus());
	    curPCMsg.setUser(pcmsg.getUser());
	    curPCMsg.setTag(pcmsg.getTag());

	    return pcmsgRepository.save(curPCMsg);
	}

	@Override
	@Transactional(readOnly = false)
	public void updatePCMsgStatusById(Integer id, String status) {
		changePCMsgStatusById(id,status);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deletePCMsgById(Integer id) {
		deletePCMsgAndChildren(id);
	}
}