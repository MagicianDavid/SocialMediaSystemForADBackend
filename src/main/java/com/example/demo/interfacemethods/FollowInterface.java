package com.example.demo.interfacemethods;

import java.util.List;

import com.example.demo.model.FollowList;
import com.example.demo.model.Follower;
import com.example.demo.model.Following;
import com.example.demo.model.User;

public interface FollowInterface {
    void followUser(int follower, int followedUser);
    void unfollowUser(int follower, int followedUser);
    List<Follower> getFollowers(User user);
    List<Following> getFollowings(User user);
    
    
	List<FollowList> getFollowers2(int user);
	List<FollowList> getFollowings2(int user);
}