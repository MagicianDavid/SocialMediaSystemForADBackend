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
public class Following {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "Following Id")
	private User followingUser;
	
	@Column (name = "Following Time")
	private LocalDateTime followingTime;
	
	
	public Following() {}
	
	public Following(LocalDateTime followingTime, User followingUser) {
		this.followingTime = followingTime; 
		this.followingUser = followingUser;
	}

	
	//	Getters and Setters
	public User getFollowingUser() {
		return followingUser;
	}

	public void setFollowingUser(User followingUser) {
		this.followingUser = followingUser;
	}

	public LocalDateTime getFollowingTime() {
		return followingTime;
	}

	public void setFollowingTime(LocalDateTime followingTime) {
		this.followingTime = followingTime;
	}

	
}
