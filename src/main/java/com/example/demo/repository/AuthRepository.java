package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Auth;

public interface AuthRepository  extends JpaRepository<Auth,Integer>{
	
	@Query("SELECT COUNT(a) > 0 FROM Auth a WHERE a.rank = :rank AND a.menuViewJason = :menuViewJason")
	public Boolean checkDuplicateRankAndMenuViewJason(String rank,String menuViewJason);
	
	@Query("SELECT a FROM Auth a JOIN a.employees e WHERE e.id = :userId")
	public Auth findByUserId(Integer userId);
}
