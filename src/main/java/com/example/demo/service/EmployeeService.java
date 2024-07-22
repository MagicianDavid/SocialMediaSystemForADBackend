package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.DuplicateEmployeeException;
import com.example.demo.interfacemethods.EmployeeInterface;
import com.example.demo.model.Auth;
import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.util.PasswordUtil;

@Service      
@Transactional(readOnly = true)
public class EmployeeService implements EmployeeInterface {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthRepository authRepository;
	
	// can not save or update the same employee/user
	// use this method to handleDuplicateEmployee and throw exceptions accordingly 
	private void handleDuplicateEmployee(Employee employee) {
		// check user_name & email
		// find employee by user_name
		List<Employee> findEmployeeList = employeeRepository.findByUserNameIgnoreCase(employee.getUsername());
		if (findEmployeeList.size() > 0) {
			List<String> emailList = findEmployeeList.stream().map(f -> f.getEmail().toLowerCase()).distinct()
					.collect(Collectors.toList());
			// if same user_name and same email, we consider it the same employee
			if (emailList.contains(employee.getEmail().toLowerCase())) {
				// save fail
				throw new DuplicateEmployeeException("Employee already exists: " + employee.getUsername());
			}
		}else {
			// no user found, then throw error
//			throw new RuntimeException("Employee not found with user_name:" + employee.getUsername());
		}
	}

	// used for user log in
	@Override
	public Employee authenicate(String username, String pwd) {
		Employee checkEmployee = employeeRepository.findByUserName(username);
        if (checkEmployee != null) {
            // check password
        	boolean isMathched = false;
        	try {
        		isMathched = PasswordUtil.decryptPassword(checkEmployee.getPassword()).equals(PasswordUtil.decryptPassword(pwd));
        	} catch (Exception e) {
    			throw new RuntimeException("Failed to decrypt pwd with employee " + username, e);
    		}
        	if(isMathched) {
            	return checkEmployee;
            } else {
            	throw new RuntimeException("Wrong username or pwd!");
            }
            
        }
        return null;
	}

	@Override
	@Transactional(readOnly = false)
	public Employee registerEmployee(Employee employee) {
		// Check if the user_name already exists
		if (employeeRepository.findByUserName(employee.getUsername()) != null) {
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
		
		employee.setRole(defaultRole);
		employee.setAuth(defaultAuth);
		// set the register date as the join date
		employee.setJoinDate(LocalDate.now());
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public List<Employee> findEmployeesByName(String name) {
		return employeeRepository.findByName(name);
	}

	@Override
	public List<Employee> findEmployeesByRole(String role) {
		return employeeRepository.findhByJobRole(role);
	}

	@Override
	public Employee findEmployeeById(Integer id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with ID:" + id));
	}

	@Override
	@Transactional(readOnly = false)
	public Employee saveEmployee(Employee employee) {
		handleDuplicateEmployee(employee);
		return employeeRepository.save(employee);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteEmployeeById(Integer id) {
		try {
			employeeRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete employee with ID: " + id, e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public Employee updateEmployee(Integer id, Employee employee) {
		Employee curEmployee = findEmployeeById(id);
		// we do all the updates here and let the controller do the logic to choose
		// whatever to update.
		curEmployee.setName(employee.getName());
		curEmployee.setEmail(employee.getEmail());
		curEmployee.setRole(employee.getRole());
		curEmployee.setPassword(employee.getPassword());
		curEmployee.setUsername(employee.getUsername());
		curEmployee.setPhoneNum(employee.getPhoneNum());
		curEmployee.setAuth(employee.getAuth());
		curEmployee.setRole(employee.getRole());
		curEmployee.setJoinDate(employee.getJoinDate());
		return curEmployee;
	}
}
