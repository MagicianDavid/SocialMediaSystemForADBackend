package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.DuplicateEmployeeException;
import com.example.demo.interfacemethods.UserInterface;
import com.example.demo.model.Auth;
import com.example.demo.model.User;
import com.example.demo.model.Role;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.RoleRepository;
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
		// find employee by user_name
		List<User> findUserList = userRepository.findByUserNameIgnoreCase(user.getUsername());
		if (findUserList.size() > 0) {
			List<String> emailList = findUserList.stream().map(f -> f.getEmail().toLowerCase()).distinct()
					.collect(Collectors.toList());
			// if same user_name and same email, we consider it the same employee
			if (emailList.contains(user.getEmail().toLowerCase())) {
				// save fail
				throw new DuplicateEmployeeException("User already exists: " + user.getUsername());
			}
		}else {
			// no user found, then throw error
//			throw new RuntimeException("Employee not found with user_name:" + employee.getUsername());
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
    			throw new RuntimeException("Failed to decrypt pwd with employee " + username, e);
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
			throw new DuplicateEmployeeException("User Name already exists.");
		}

		// Encrypt the password before saving (do it at front end	)
		// can do the encryption both back end & front end
		// employee.setPassword(PasswordUtil.encodePassword(employee.getPassword()));
		
		// Set the default role and authorization for new employees
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
		return userRepository.save(user);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findUserByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public List<User> findUserByRole(String role) {
		return userRepository.findhByJobRole(role);
	}

	@Override
	public User findUserById(Integer id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with ID:" + id));
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
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete employee with ID: " + id, e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public User updateUser(Integer id, User user) {
		User curUser = findUserById(id);
		// we do all the updates here and let the controller do the logic to choose
		// whatever to update.
		curUser.setName(user.getName());
		curUser.setEmail(user.getEmail());
		curUser.setRole(user.getRole());
		curUser.setPassword(user.getPassword());
		curUser.setUsername(user.getUsername());
		curUser.setPhoneNum(user.getPhoneNum());
		curUser.setAuth(user.getAuth());
		curUser.setRole(user.getRole());
		curUser.setJoinDate(user.getJoinDate());
		return curUser;
	}
}
