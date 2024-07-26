package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.model.Follower;
import com.example.demo.model.User;

public interface FollowerRepository extends JpaRepository<Follower, Integer> {
    List<Follower> findByFollowedUserOrderByFollowedTimeDesc(User followedUser);
}