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
public class Notification {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "Notification Id")
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "Banned User id")
	private User notificationUser;
	
	@Column (name = "Message", length = 255)
	private String message;
	
	@Column (name = "Notification Time")
	private LocalDateTime notificationTime;
	
	@Column (name = "Status")
	private NotificationStatus notificationStatus;
	
	
	public Notification () {}
	public Notification (User notificationUser, String message, LocalDateTime notificationTime, NotificationStatus notificationStatus) {
		this.notificationUser = notificationUser;
		this.message = message;
		this.notificationTime = notificationTime;
		this.notificationStatus = notificationStatus;
	}
	
	//	Getters and Setters
	public User getUser() {
		return notificationUser;
	}
	public void setUser(User notificationUser) {
		this.notificationUser = notificationUser;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getNotificationTime() {
		return notificationTime;
	}
	public void setNotificationTime(LocalDateTime notificationTime) {
		this.notificationTime = notificationTime;
	}
	public NotificationStatus getNotificationStatus() {
		return notificationStatus;
	}
	public void setNotificationStatus(NotificationStatus notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
	
	
	
}
