package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query("Select e from Employee as e where e.name like CONCAT('%',:k,'%') ")
	List<Employee> findByName(@Param("k") String keyword);

	@Query("Select e from Employee e where e.role.type = :role ")
	List<Employee> findhByJobRole(@Param("role") String role);

	@Query("SELECT e FROM Employee e WHERE (LOWER(e.username)= LOWER(:uname) or LOWER(e.email) = Lower(:uname)) AND e.password=:pwd")
	Employee findByNamePwd(@Param("uname") String username, @Param("pwd") String pwd);

	@Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.role.id = :id")
	Boolean hasWithThisRole(@Param("id") int roleId);

	// ignore upper and lower case
	@Query("SELECT e FROM Employee e WHERE LOWER(e.username) = LOWER(:username)")
	List<Employee> findByUserNameIgnoreCase(@Param("username") String username);
	
	@Query("SELECT e FROM Employee e WHERE e.username = :username")
	Employee findByUserName(@Param("username") String username);

}
