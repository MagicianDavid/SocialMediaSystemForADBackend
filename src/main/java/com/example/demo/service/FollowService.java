package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.interfacemethods.FollowInterface;
import com.example.demo.model.FollowList;
import com.example.demo.model.Follower;
import com.example.demo.model.Following;
import com.example.demo.model.User;
import com.example.demo.repository.FollowListRepository;
import com.example.demo.repository.FollowerRepository;
import com.example.demo.repository.FollowingRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional (readOnly = true)
public class FollowService implements FollowInterface {

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired 
    private FollowListRepository followListRepository;
    
    
    @Override
    public List<User> getFollowers2(User user) {
    	 List<FollowList> followers = followListRepository.findByFollowedUser(user);
         return followers.stream()
                         .map(FollowList::getFollower)
                         .collect(Collectors.toList());
    }

    @Override
    public List<User> getFollowings2(User user) {
    	List<FollowList> followings = followListRepository.findByFollower(user);
        return followings.stream()
                         .map(FollowList::getFollowedUser)
                         .collect(Collectors.toList());
    }
    
        
    @Override
    @Transactional(readOnly = false)
    public void followUser(User follower, User followedUser) {
          FollowList followList = new FollowList();
          followList.setFollower(follower);
          followList.setFollowedUser(followedUser);
          followList.setFollowedTime(LocalDateTime.now());
          followListRepository.save(followList);
    	
//        Follower newFollower = new Follower();
//        newFollower.setFollowedUser(followedUser);
//        newFollower.setFollowedTime(LocalDateTime.now());
//        followerRepository.save(newFollower);
//
//        Following newFollowing = new Following();
//        newFollowing.setFollowingUser(follower);
//        newFollowing.setFollowingTime(LocalDateTime.now());
//        followingRepository.save(newFollowing);
    }
    

    @Override
    @Transactional(readOnly = false)
    public void unfollowUser(User follower, User followedUser) {
    	followListRepository.deleteByFollowerAndFollowedUser(follower, followedUser);

//        Follower existingFollower = followerRepository.findByFollowedUserOrderByFollowedTimeDesc(followedUser)
//                .stream()
//                .filter(f -> f.getFollowedUser().equals(follower))
//                .findFirst()
//                .orElse(null);
//
//        if (existingFollower != null) {
//            followerRepository.delete(existingFollower);
//        }
//
//        Following existingFollowing = followingRepository.findByFollowingUserOrderByFollowingTimeDesc(follower)
//                .stream()
//                .filter(f -> f.getFollowingUser().equals(followedUser))
//                .findFirst()
//                .orElse(null);
//
//        if (existingFollowing != null) {
//            followingRepository.delete(existingFollowing);
//        }
    }

    @Override
    public List<Follower> getFollowers(User user) {
        return followerRepository.findByFollowedUserOrderByFollowedTimeDesc(user);
    }

    @Override
    public List<Following> getFollowings(User user) {
        return followingRepository.findByFollowingUserOrderByFollowingTimeDesc(user);
    }
}
