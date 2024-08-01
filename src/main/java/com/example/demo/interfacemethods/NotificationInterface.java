package com.example.demo.interfacemethods;

import java.util.List;

import com.example.demo.model.Notification;

public interface NotificationInterface {
	
	Notification findNotificationById(Integer id);
	List<Notification> findAllNotifications();
	List<Notification> findAllUnReadNotifications();
	List<Notification> findAllReadNotifications();
	Boolean isNotificationReadById(Integer id);
	// CRUD
	Notification saveNotification(Notification notification);
	Notification updateNotificationById(Integer id, Notification newNotification);
	void updateNotificationStatusById(Integer id);
	void deleteNotificationById(Integer id);
}
