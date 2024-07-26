package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Follower;
import com.example.demo.model.Following;
import com.example.demo.model.User;
import com.example.demo.service.FollowService;
import com.example.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    @PostMapping("/{followerId}/follow/{followedUserId}")
    public ResponseEntity<String> followUser(@PathVariable Integer followerId, @PathVariable Integer followedUserId) {
        User follower = userService.findUserById(followerId);
        User followedUser = userService.findUserById(followedUserId);
        followService.followUser(follower, followedUser);
        return ResponseEntity.ok("User followed successfully.");
    }

    @DeleteMapping("/{followerId}/unfollow/{followedUserId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Integer followerId, @PathVariable Integer followedUserId) {
        User follower = userService.findUserById(followerId);
        User followedUser = userService.findUserById(followedUserId);
        followService.unfollowUser(follower, followedUser);
        return ResponseEntity.ok("User unfollowed successfully.");
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<Follower>> getFollowers(@PathVariable Integer userId) {
        User user = userService.findUserById(userId);
        List<Follower> followers = followService.getFollowers(user);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{userId}/followings")
    public ResponseEntity<List<Following>> getFollowings(@PathVariable Integer userId) {
        User user = userService.findUserById(userId);
        List<Following> followings = followService.getFollowings(user);
        return ResponseEntity.ok(followings);
    }
}

