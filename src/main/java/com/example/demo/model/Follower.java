package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Follower {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "Follower Id")
	private User followedUser;

	@Column (name = "Followed Time")
	private LocalDateTime followedTime;
	
	
	public Follower() {}
	
	public Follower(LocalDateTime followedTime, User followedUser) {
		this.followedTime = followedTime; 
		this.followedUser = followedUser;
		}

	
	//	Getters and Setters
	public User getFollowedUser() {
		return followedUser;
	}

	public void setFollowedUser(User followedUser) {
		this.followedUser = followedUser;
	}

	public LocalDateTime getFollowedTime() {
		return followedTime;
	}

	public void setFollowedTime(LocalDateTime followedTime) {
		this.followedTime = followedTime;
	}
	
}
	
