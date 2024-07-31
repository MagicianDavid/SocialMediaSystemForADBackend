package com.example.demo.interfacemethods;

import java.util.List;

import com.example.demo.model.User;

public interface UserInterface {
	List<User> findAllUsers();
	List<User> findUsersByName(String name);
	List<User> findUsersByRole(String role);
	User findUserById(Integer id);	
	
	User saveUser(User user);
	void updateUserStatusById(Integer id,String status);
	void blockUserById(Integer UserId, Integer blockId);
	User updateUser(Integer id, User user);

	//Check user
	User authenicate (String username, String pwd);
	User registerUser(User user);
}
