package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmailRequest;
import com.example.demo.interfacemethods.BanHistoryInterface;
import com.example.demo.interfacemethods.NotificationInterface;
import com.example.demo.interfacemethods.UserInterface;
import com.example.demo.model.Auth;
import com.example.demo.model.Notification;
import com.example.demo.model.NotificationStatus;
import com.example.demo.model.User;
import com.example.demo.model.UserStatus;
import com.example.demo.service.EmailService;

@RestController
@RequestMapping("/api/checkThenBan")
public class CheckThenBanUserController {
	@Autowired
	private BanHistoryInterface banHistoryService;
	
	@Autowired
	private UserInterface userService;
	
	@Autowired
	private NotificationInterface notificationService;
	
	@Autowired
    private EmailService emailService;
	
	// check user and change its status & authorization & socialScore accordingly
    @PutMapping("/{userId}")
    public ResponseEntity<Void> checkUserSocialScoreThenUpdateStatusAndAuth(@PathVariable("userId") Integer userId) {
    	if (banHistoryService.IsWithinBanPeriodByUserId(userId)) {
    		// this user already in ban period
    		// do nothing
    		return ResponseEntity.noContent().build();
    	} else {
    		// if not banned 
    		// check social Score and get Authorization Rank
    		// because socialScore 1-60 have the same authorization, 
    		// we use fistRandList to set them as one authorization called FixedRankForSocialScore:1-60
    		// others are like:
    		// socialScore 61-70 --> FixedRankForSocialScore:61-70
    		// socialScore 71-70 --> FixedRankForSocialScore:71-80
    		// ...
    		// ...
    		// socialScore 111-120 --> FixedRankForSocialScore:111-120
    		User curUser = userService.findUserById(userId);
    		Integer olderAuthId = curUser.getAuth().getId();
    		// return adjusted authorization
    		Auth adjustAuth = userService.checkUserSocialScoreThenAdjustAuth(userId);
    		
    		// if Authorization has been updated then we do the notifications or email
    		if (!olderAuthId.equals(adjustAuth.getId())) {
    			// then check the status whether should be updated
    			if (adjustAuth.getRank().equals("FixedRankForSocialScore:0")) {
    				
    				// currently we ban this user for a year, after one year his score & status & authorization will be reset
    				userService.updateUserStatusById(userId,UserStatus.ban);
    				// ban this user and send email
    				String systemAutoBanEmialSubject = "System-Auto-Ban-For-Aggresive-Behaviours";
    				String systemAutoBanEmialText = "We're very sorry to inform you that you've been baned.";
    				EmailRequest systemAutoBanEmial = new EmailRequest(curUser.getEmail(),systemAutoBanEmialSubject,systemAutoBanEmialText);
    				try {
    		            emailService.sendSimpleMessage(systemAutoBanEmial.getTo(), systemAutoBanEmial.getSubject(), systemAutoBanEmial.getText());
    		        } catch (Exception e) {
    		        	throw new RuntimeException("Failed to send email\n"+e.getMessage());
    		        }
    			} else {
    				// since authorization has already been updated
    				// status also will not change since this user is not banned,
    				// it will remain as "active"
    				// simply notify this user when his/her authorization has been updated
    				String notifMsg = "System-Auto-Generated-Notification\n"
							+"--Your Authorization has been updated--";
					LocalDateTime notifDateTime = LocalDateTime.now();
					Notification systemGeneratedNotif = new Notification(curUser,notifMsg,notifDateTime, NotificationStatus.Unread);
					notificationService.saveNotification(systemGeneratedNotif);
    			}
    		}
            
    	}
        return ResponseEntity.noContent().build();
    }
}