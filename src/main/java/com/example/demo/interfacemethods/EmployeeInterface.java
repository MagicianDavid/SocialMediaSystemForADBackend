package com.example.demo.interfacemethods;

import java.util.List;

import com.example.demo.model.Employee;

public interface EmployeeInterface {
	List<Employee> findAllEmployees();
	List<Employee> findEmployeesByName(String name);
	List<Employee> findEmployeesByRole(String role);
	Employee findEmployeeById(Integer id);	
	
	Employee saveEmployee(Employee employee);
	void deleteEmployeeById(Integer id);
	Employee updateEmployee(Integer id, Employee employee);
	
	//Check user
	Employee authenicate (String username, String pwd);
	Employee registerEmployee(Employee employee);
}
