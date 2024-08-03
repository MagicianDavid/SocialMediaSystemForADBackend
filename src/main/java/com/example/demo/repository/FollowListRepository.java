package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.FollowList;
import com.example.demo.model.User;

public interface FollowListRepository extends JpaRepository<FollowList, Integer>{
	
//    List<FollowList> findByFollowedUserOrderByFollowedTimeDesc(User followedUser);
	
    List<FollowList> findByFollower(User follower);
    List<FollowList> findByFollowedUser(User followedUser);
    void deleteByFollowerAndFollowedUser(User follower, User followedUser);

//    void deleteByFollowingUserAndFollowedUser(User followingUser, User followedUser);

}
