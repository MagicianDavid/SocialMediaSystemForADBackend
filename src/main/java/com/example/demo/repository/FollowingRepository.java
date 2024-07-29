package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Following;
import com.example.demo.model.User;

import java.util.List;

public interface FollowingRepository extends JpaRepository<Following, Integer> {
    List<Following> findByFollowingUserOrderByFollowingTimeDesc(User followingUser);
}