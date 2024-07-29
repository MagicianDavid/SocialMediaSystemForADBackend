package com.example.demo.test;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Auth;
import com.example.demo.model.Role;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;


@SpringBootTest
public class JUnit_testdata {

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private AuthRepository authRepo;

    @Autowired
    private EmployeeRepository empRepo;
    
    @Test    
    public void testCreateRole() {
        Role role = new Role();
        role.setId(1);
        role.setType("R1");
        roleRepo.saveAndFlush(role);
    }


    @Test
    public void testCreateAuth(){
        Auth auth = new Auth("R1", "menuViewJasonData");
    	auth.setMenuViewJason(null);
    }
    
    
    
//    @Test
//    public void testFindPostById() {
//    	Auth auth = new Auth();
//    	auth.setId(1);
//    	auth.setRank(null);
//
//        postRepository.saveAndFlush(post);
//    }
	
	
}
