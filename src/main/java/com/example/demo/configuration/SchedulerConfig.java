package com.example.demo.configuration;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 0 */7 * ?") // every 7 days at midnight
    public void incrementSocialScores() {
        userService.incrementAllUserSocialScores();
    }
}