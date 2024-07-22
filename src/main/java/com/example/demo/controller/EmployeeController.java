package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.exception.DuplicateEmployeeException;
import com.example.demo.exception.DuplicateTypeException;
import com.example.demo.interfacemethods.EmployeeInterface;
import com.example.demo.model.Employee;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeInterface employeeService;

    // check user_name and password if match then login
    @PostMapping("/login")
    public ResponseEntity<Employee> login(@RequestBody LoginRequest loginRequest ) {
        Employee employee = employeeService.authenicate(loginRequest.getUsername(), loginRequest.getPassword());
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {
        try {
            Employee registeredEmployee = employeeService.registerEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredEmployee);
        } catch (DuplicateTypeException e) {
        	return ResponseEntity.status(409).build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer id) {
        Employee employee = employeeService.findEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable("name") String name) {
        List<Employee> employees = employeeService.findEmployeesByName(name);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/findByRole/{role}")
    public ResponseEntity<List<Employee>> getEmployeeByRole(@PathVariable("role") String role) {
        List<Employee> employees = employeeService.findEmployeesByRole(role);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/create")
    public ResponseEntity<List<Employee>> saveEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        List<Employee> employees = employeeService.findAllEmployees();
        return ResponseEntity.status(HttpStatus.CREATED).body(employees);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<List<Employee>> updateEmployee(@PathVariable("id") Integer id, @RequestBody Employee updateEmployee) {
        employeeService.updateEmployee(id, updateEmployee);
        List<Employee> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.deleteEmployeeById(id);
//        List<Employee> employees = employeeService.findAllEmployees();
        return ResponseEntity.noContent().build();
    }
    
    @ExceptionHandler(DuplicateEmployeeException.class)
    public ResponseEntity<String> handleDuplicateEmployeeException(DuplicateEmployeeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
