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
public class BanHistory {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name= "Ban Id")
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "Banned User id")
	private User user;

	@Column (name = "Ban Time")
	private LocalDateTime banTime;
	
	@Column (name = "Ban Duration")
	private Integer banDuration;
	
	
	
	public BanHistory() {}
	
	public BanHistory(LocalDateTime banTime, Integer banDuration) {
		this.banTime = banTime; 
		this.banDuration = banDuration;
	}

	
//	Getters and Setters
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getBanTime() {
		return banTime;
	}

	public void setBanTime(LocalDateTime banTime) {
		this.banTime = banTime;
	}

	public Integer getBanDuration() {
		return banDuration;
	}

	public void setBanDuration(Integer banDuration) {
		this.banDuration = banDuration;
	}


}
