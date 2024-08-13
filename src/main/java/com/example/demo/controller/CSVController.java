package com.example.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.interfacemethods.UserInterface;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/download")
public class CSVController {
	
	@Autowired
	private UserInterface userService;
	
	@GetMapping("/user_csv")
	public void downloadUsers(HttpServletResponse response) throws IOException {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=users.csv");

		List<User> users = userService.findAllUsers();

		try (PrintWriter writer = response.getWriter()) {
			writer.write("ID,Name,Username,Status,Country,Gender,SocialScore,JoinedDate\n");
			for (User user : users) {
				writer.write(user.getId() + "," + user.getName() +","+ user.getUsername()+","+ 
			                 user.getStatus() +","+ user.getCountry() + "," + user.getGender()
			                 + ","+ user.getSocialScore() +"," + user.getJoinDate() + "\n");
			}
		}
	}
	
	

}
