package com.example.demo.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column (length = 255)
	private String name;
	
	@Column (length = 255)
	private String email;
	
	@Column (length = 35)
	private String username;
	
	@Column (length = 255)
	private String password;
	
	@Column(name = "Phone_Number", length = 15)
	private String phoneNum;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate joinDate;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name = "auth_id")
	private Auth auth;
	
	public Employee() {}
	
	public Employee(String name, String email, 
			Role role, String username, String password, Employee supervisor, 
			String phoneNum, LocalDate joinDate) 
	{
		this.name = name;
		this.email = email;
		this.role = role;
		this.username = username;
		this.password = password;
		this.phoneNum = phoneNum;
		this.joinDate = joinDate;

	}

	//getter and setter

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}
	
	public Auth getAuth() {
		return auth;
	}
	
	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	
}
