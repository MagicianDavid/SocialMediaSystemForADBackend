package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.exception.DuplicateUserException;
import com.example.demo.exception.DuplicateTypeException;
import com.example.demo.interfacemethods.UserInterface;
import com.example.demo.model.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserInterface userService;

    // check user_name and password if match then login
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest ) {
        User user = userService.authenicate(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (DuplicateTypeException e) {
        	return ResponseEntity.status(409).build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable("id") Integer id) {
        User user = userService.findUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<User>> getUserByName(@PathVariable("name") String name) {
        List<User> users = userService.findUsersByName(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findByRole/{role}")
    public ResponseEntity<List<User>> getUserByRole(@PathVariable("role") String role) {
        List<User> users = userService.findUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<List<User>> saveUser(@RequestBody User user) {
    	userService.saveUser(user);
        List<User> employees = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.CREATED).body(employees);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<List<User>> updateUser(@PathVariable("id") Integer id, @RequestBody User updateUser) {
        userService.updateUser(id, updateUser);
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/ban/{id}")
    public ResponseEntity<Void> baneUser(@PathVariable("id") Integer id) {
        userService.banUserById(id);
        return ResponseEntity.noContent().build();
    }
    
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleDuplicateUserException(DuplicateUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
