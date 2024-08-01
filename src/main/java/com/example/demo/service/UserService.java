package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
			userRepository.save(curUser);
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
        		// check user status if not active we don't allow this user log in
        		if (checkUser.getStatus().equals("active")) {
                	return checkUser;	
        		} else {
        			throw new RuntimeException("No longer active user!");
        		}
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
	public void updateUserStatusById(Integer id, String status) {
		changeUserStatusById(id,status);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateUserSocialScoresById(Integer id, String operation,Integer adjustScore) {
		try {
			User curUser = findUserById(id);
			Integer curScore = curUser.getSocialScore();
			if (operation == "add") {
				curScore+=adjustScore;
			} else if (operation == "minus") {
				curScore-=adjustScore;
			}
			if (curScore > 120) curScore = 120;
			if (curScore < 0) curScore = 0;
			curUser.setSocialScore(curScore);
			userRepository.save(curUser);
		} catch (Exception e) {
			throw new RuntimeException("Failed to " + operation + " user socialScore with ID: " + id, e);
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void blockUserById(Integer UserId, Integer blockUserId) {
		User curUser = findUserById(UserId);
		// to check whether we have this blockUser
		findUserById(blockUserId);
        
        String blockList = curUser.getBlockList();
		// check whether blockList contains this id
        if (blockList == null || blockList.isEmpty()) {
            blockList = blockUserId.toString();
        } else {
        	List<String> blockIds = new ArrayList<>(Arrays.asList(blockList.split(",")));
        	if (blockIds.contains(blockUserId.toString())) {
        		blockIds.remove(blockUserId.toString());
        		blockList = String.join(",", blockIds);
        	} else {            
            	blockList += "," + blockUserId;
            }
        }
        curUser.setBlockList(blockList);
        userRepository.save(curUser);
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
		return userRepository.save(curUser);
	}

	@Override
	@Transactional(readOnly = false)
	public User checkUserSocialScoreThenUpdateStatusAndAuth(Integer userId) {
		User curUser = findUserById(userId);
		Integer curSocialScore = curUser.getSocialScore();
		switch((curSocialScore + 9) / 10) {
			case 12:
				// 111-120
//				authRepository.findByRank("L12");
				break;
			case 11:
				// 101-110
				break;
			case 10:
				// 91-100
				break;
			case 9:
				// 81-90
				break;
			case 8:
				// 71-80
				break;
			case 7:
				// 61-70
				break;
			case 6:
			case 5:
			case 4:
			case 3:
			case 2:
			case 1:
				// 1-60
				
			case 0:
				curUser.setStatus("ban");
//	    		curUser.setAuth();
	        default:
	            // default setting user status to "active" & socialScore to 120
	    		curUser.setStatus("active");
	    		curUser.setAuth(curUser.getAuth());
	    		curUser.setSocialScore(120);
	            break;
		}
		return userRepository.save(curUser);
	}
}