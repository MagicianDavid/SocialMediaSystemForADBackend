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
	private User user;
	
	@Column (name = "Following Time")
	private LocalDateTime followingTime;
	
	
	public Following() {}
	
	public Following(LocalDateTime followingTime) {
		this.followingTime = followingTime; 
		}

	
	//	Getters and Setters
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getFollowingTime() {
		return followingTime;
	}

	public void setFollowingTime(LocalDateTime followingTime) {
		this.followingTime = followingTime;
	}

	
}
