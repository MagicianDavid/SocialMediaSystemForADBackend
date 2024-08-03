package com.example.demo.interfacemethods;

import java.util.List;

import com.example.demo.model.FollowList;
import com.example.demo.model.Follower;
import com.example.demo.model.Following;
import com.example.demo.model.User;

public interface FollowInterface {
    void followUser(User follower, User followedUser);
    void unfollowUser(User follower, User followedUser);
    List<Follower> getFollowers(User user);
    List<Following> getFollowings(User user);
    
    
	List<User> getFollowers2(User user);
	List<User> getFollowings2(User user);
}