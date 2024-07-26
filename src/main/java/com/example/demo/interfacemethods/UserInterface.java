package com.example.demo.interfacemethods;

import java.util.List;

import com.example.demo.model.User;

public interface UserInterface {
	List<User> findAllUsers();
	List<User> findUserByName(String name);
	List<User> findUserByRole(String role);
	User findUserById(Integer id);	
	
	User saveUser(User employee);
	void deleteUserById(Integer id);
	User updateUser(Integer id, User employee);
	
	//Check user
	User authenicate (String username, String pwd);
	User registerUser(User user);


}
