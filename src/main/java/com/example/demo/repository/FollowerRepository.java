package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.example.demo.model.Follower;
import com.example.demo.model.User;

public interface FollowerRepository extends JpaRepository<Follower, Integer> {
    List<Follower> findByFollowedUserOrderByFollowedTimeDesc(User followedUser);

    
//    @Query("SELECT COUNT(*) FROM Follower f WHERE f.id = :userId ")
//    Integer findcountFollowerByUserId(@Param("userId") int userId);
}