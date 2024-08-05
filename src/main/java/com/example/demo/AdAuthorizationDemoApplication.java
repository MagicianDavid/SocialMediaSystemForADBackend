package com.example.demo;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import com.example.demo.model.PCMsg;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.PCMsgRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.statusEnum.UserStatus;


@SpringBootApplication
@EnableSpringDataWebSupport
public class AdAuthorizationDemoApplication {

	public static void main(String[] args) {
		System.out.println("TEAM-07 NUS ISS");
		SpringApplication.run(AdAuthorizationDemoApplication.class, args);	
	}
	
	
	
	 @Bean
	 public CommandLineRunner initializeDatabase(UserRepository userRepository, 
			 PCMsgRepository pcMsgRepository, RoleRepository roleRepository) {
	        return args -> {
	            // Create roles
	            Role moderatorRole = new Role("Moderator");
	            Role regularUserRole = new Role("User");

	            // Save roles
	            roleRepository.save(moderatorRole);
	            roleRepository.save(regularUserRole);

	            // Create users
	            User james = new User(
	                "James", 
	                "james@example.com", 
	                moderatorRole, // James is a moderator
	                null, 
	                "james123", 
	                "password", 
	                "Male", 
	                "USA", 
	                UserStatus.active, 
	                120, 
	                "", 
	                "1234567890", 
	                LocalDate.now()
	            );

	            User mary = new User(
	                "Mary", 
	                "mary@example.com", 
	                regularUserRole, // Regular user
	                null, 
	                "mary123", 
	                "password", 
	                "Female", 
	                "UK", 
	                UserStatus.active, 
	                119, //high social media score
	                "", 
	                "0987654321", 
	                LocalDate.now()
	            );

	            User alex = new User(
	                "Alex", 
	                "alex@example.com", 
	                regularUserRole, // Regular user
	                null, 
	                "alex123", 
	                "password", 
	                "Male", 
	                "Canada", 
	                UserStatus.active, 
	                70, // low social media score
	                "", 
	                "1234567890", 
	                LocalDate.now()
	            );

	            User zark = new User(
	                "Zark", 
	                "zark@example.com", 
	                regularUserRole, // Regular user
	                null, 
	                "zark123", 
	                "password", 
	                "Male", 
	                "Australia", 
	                UserStatus.ban, 
	                0, // account banned
	                "", 
	                "0123456789", 
	                LocalDate.now()
	            );

	            // Save users
	            userRepository.save(james);
	            userRepository.save(mary);
	            userRepository.save(alex);
	            userRepository.save(zark);

	            // Create posts
	            PCMsg alexPost = new PCMsg(
	                null, 
	                "This is a spam content", 
	                "2024-08-05T12:00:00", 
	                false, 
	                "Spam", 
	                12345
	            );
	            alexPost.setUser(alex);
	            PCMsg zarkPost = new PCMsg(
	                "http://example.com/image.jpg", 
	                "This is a negative content", 
	                "2024-08-05T12:00:00", 
	                true, 
	                "Aggresive", 
	                54321
	            );
	            zarkPost.setUser(zark);

	            // Save posts
	            pcMsgRepository.save(alexPost);
	            pcMsgRepository.save(zarkPost);
	        };
	    }

}
