package com.example.demo.controller;

import com.example.demo.model.Notification;
import com.example.demo.dto.EmailRequest;
import com.example.demo.interfacemethods.NotificationInterface;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationInterface notificationService;
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendSimpleMessage(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email\n"+e.getMessage());
        }
    }

    @GetMapping("/unReadList")
    public List<Notification> getAllUnreadNotifications() {
        return notificationService.findAllUnReadNotifications();
    }

    @GetMapping("/readList")
    public List<Notification> getAllReadNotifications() {
        return notificationService.findAllReadNotifications();
    }

    @GetMapping("/isRead/{id}")
    public Boolean isNotificationRead(@PathVariable Integer id) {
        return notificationService.isNotificationReadById(id);
    }

    @PutMapping("/updateStatusToRead/{id}")
    public void markNotificationAsRead(@PathVariable Integer id) {
        notificationService.updateNotificationStatusById(id);
    }

    @PostMapping("/create")
    public Notification saveNotification(@RequestBody Notification notification) {
        return notificationService.saveNotification(notification);
    }

    @PutMapping("/update/{id}")
    public Notification updateNotification(@PathVariable Integer id, @RequestBody Notification newNotification) {
        return notificationService.updateNotificationById(id, newNotification);
    }

    @DeleteMapping("/delete{id}")
    public void deleteNotification(@PathVariable Integer id) {
        notificationService.deleteNotificationById(id);
    }
}
