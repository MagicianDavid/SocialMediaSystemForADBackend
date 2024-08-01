package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.interfacemethods.NotificationInterface;
import com.example.demo.model.Notification;
import com.example.demo.model.NotificationStatus;
import com.example.demo.repository.NotificationRepository;

@Service      
@Transactional(readOnly = true)
public class NotificationService implements NotificationInterface {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Override
	public Notification findNotificationById(Integer id) {
		return notificationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Notification not found with ID:" + id));
	}
	
	@Override
	public List<Notification> findAllNotifications() {
		return notificationRepository.findAll();
	}

	@Override
	public List<Notification> findAllUnReadNotifications() {
		return notificationRepository.findByNotificationStatus(NotificationStatus.Unread);
	}

	@Override
	public List<Notification> findAllReadNotifications() {
		return notificationRepository.findByNotificationStatus(NotificationStatus.Read);
	}

	@Override
	public Boolean isNotificationReadById(Integer id) {
		Notification notification = findNotificationById(id);
        return notification.getNotificationStatus() == NotificationStatus.Read;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateNotificationStatusById(Integer id) {
		// can only change unread to read
		Notification notification = findNotificationById(id);
		Boolean isRead = notification.getNotificationStatus() == NotificationStatus.Read;
		if (!isRead) notification.setNotificationStatus(NotificationStatus.Read);
		notificationRepository.save(notification);
	}

	@Override
	@Transactional(readOnly = false)
	public Notification saveNotification(Notification notification) {
		return notificationRepository.save(notification);
	}

	@Override
	@Transactional(readOnly = false)
	public Notification updateNotificationById(Integer id, Notification newNotification) {
		return notificationRepository.findById(id).map(notification -> {
            notification.setMessage(newNotification.getMessage());
            notification.setNotificationTime(newNotification.getNotificationTime());
            notification.setNotificationStatus(newNotification.getNotificationStatus());
            return notificationRepository.save(notification);
        }).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteNotificationById(Integer id) {
		notificationRepository.deleteById(id);
	}

}
