package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@RestController
public class StudentController {
	@Autowired
	private EmployeeService service;
	
	@GetMapping
	public String greet() {
		return "hello";
	}
	
	@GetMapping("/getall")
	public List<Employee> getAll() {
		return service.getAll();
	}
	
	@PostMapping("/register")
	public Employee addEmployee(@RequestBody Employee e) {
		return service.addEmployee(e);
	}
}
