package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.DuplicateUserException;
import com.example.demo.interfacemethods.UserInterface;
import com.example.demo.model.Auth;
import com.example.demo.model.User;
import com.example.demo.model.Role;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.PasswordUtil;

@Service      
@Transactional(readOnly = true)
public class UserService implements UserInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthRepository authRepository;
	
	// can not save or update the same employee/user
	// use this method to handleDuplicateEmployee and throw exceptions accordingly 
	private void handleDuplicateUser(User user) {
		// check user_name & email
		// find User by user_name
		List<User> findUserList = userRepository.findByUserNameIgnoreCase(user.getUsername());
		if (findUserList.size() > 0) {
			List<String> emailList = findUserList.stream().map(f -> f.getEmail().toLowerCase()).distinct()
					.collect(Collectors.toList());
			// if same user_name and same email, we consider it the same user
			if (emailList.contains(user.getEmail().toLowerCase())) {
				// save fail
				throw new DuplicateUserException("User already exists: " + user.getUsername());
			}
		}else {
			// no user found, then throw error
//			throw new RuntimeException("Employee not found with user_name:" + employee.getUsername());
		}
	}
	
	private void changeUserStatusById(Integer id, String operation) {
		try {
			User curUser = findUserById(id);
			curUser.setStatus(operation);
		} catch (Exception e) {
			throw new RuntimeException("Failed to " + operation + " user with ID: " + id, e);
		}
	}

	// used for user log in
	@Override
	public User authenicate(String username, String pwd) {
		User checkUser = userRepository.findByUserName(username);
        if (checkUser != null) {
            // check password
        	boolean isMathched = false;
        	try {
        		isMathched = PasswordUtil.decryptPassword(checkUser.getPassword()).equals(PasswordUtil.decryptPassword(pwd));
        	} catch (Exception e) {
    			throw new RuntimeException("Failed to decrypt pwd with user " + username, e);
    		}
        	if(isMathched) {
            	return checkUser;
            } else {
            	throw new RuntimeException("Wrong username or pwd!");
            }
            
        }
        return null;
	}

	@Override
	@Transactional(readOnly = false)
	public User registerUser(User user) {
		// Check if the user_name already exists
		if (userRepository.findByUserName(user.getUsername()) != null) {
			throw new DuplicateUserException("User Name already exists.");
		}

		// Encrypt the password before saving (do it at front end	)
		// can do the encryption both back end & front end
		// employee.setPassword(PasswordUtil.encodePassword(employee.getPassword()));
		
		// Set the default role and authorization for new employees
		// TODO: actually this kind of default setting is very strange
		// since u are hard coding the User role but what if there's no role or auth?
		Role defaultRole = roleRepository.findByTypeIgnoreCase("User");
		List<Auth> allAuth = authRepository.findAll();
		Auth defaultAuth = new Auth();
		if (allAuth.size() > 0) {
			// set the first auth as default auth
			defaultAuth = allAuth.get(0);
		} else {
			defaultAuth = null;
		}
		
		user.setRole(defaultRole);
		user.setAuth(defaultAuth);
		// set the register date as the join date
		user.setJoinDate(LocalDate.now());
		// set the default socialScore to 120
		user.setSocialScore(120);
		// set the default status to 'active'
		user.setStatus("active");
		return userRepository.save(user);
	}

	@Override
	public List<User> findAllUsers() {
		// only return users whose status is not "delete"
		return userRepository.findAll().stream().filter(u->!u.getStatus().equals("delete")).toList();
	}

	@Override
	public List<User> findUsersByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public List<User> findUsersByRole(String role) {
		return userRepository.findhByJobRole(role);
	}

	@Override
	public User findUserById(Integer id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with ID:" + id));
	}

	@Override
	@Transactional(readOnly = false)
	public User saveUser(User user) {
		handleDuplicateUser(user);
		return userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUserById(Integer id) {
		// actually here we do not remove user from our database
		// we change their status into 'delete' so that it's been hidden
		changeUserStatusById(id,"delete");
	}
	
	@Override
	@Transactional(readOnly = false)
	public void banUserById(Integer id) {
		changeUserStatusById(id,"ban");
	}

	@Override
	@Transactional(readOnly = false)
	public User updateUser(Integer id, User user) {
		User curUser = findUserById(id);
		// we do all the updates here and 
		// let the controller do the logic to choose
		// whatever to update.
		curUser.setName(user.getName());
		curUser.setEmail(user.getEmail());
		curUser.setPassword(user.getPassword());
		curUser.setUsername(user.getUsername());
		curUser.setPhoneNum(user.getPhoneNum());
		curUser.setGender(user.getGender());
		curUser.setCountry(user.getCountry());
		curUser.setBlockList(user.getBlockList());
		curUser.setSocialScore(user.getSocialScore());
		curUser.setStatus(user.getStatus());
		curUser.setAuth(user.getAuth());
		curUser.setRole(user.getRole());
		curUser.setJoinDate(user.getJoinDate());
		return curUser;
	}
}