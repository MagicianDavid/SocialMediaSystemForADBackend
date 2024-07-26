
package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("Select u from User as u where u.name like CONCAT('%',:k,'%') ")
	List<User> findByName(@Param("k") String keyword);

	@Query("Select u from User u where u.role.type = :role ")
	List<User> findhByJobRole(@Param("role") String role);

	@Query("SELECT u FROM User u WHERE (LOWER(u.username)= LOWER(:uname) or LOWER(e.email) = Lower(:uname)) AND u.password=:pwd")
	User findByNamePwd(@Param("uname") String username, @Param("pwd") String pwd);

	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.role.id = :id")
	Boolean hasWithThisRole(@Param("id") int roleId);

	// ignore upper and lower case
	@Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)")
	List<User> findByUserNameIgnoreCase(@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findByUserName(@Param("username") String username);

}
