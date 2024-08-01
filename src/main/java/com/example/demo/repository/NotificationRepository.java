package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Notification;
import com.example.demo.model.NotificationStatus;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
	List<Notification> findByNotificationStatus(NotificationStatus status);
}
