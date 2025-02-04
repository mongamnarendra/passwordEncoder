package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repo.EmployeeRepo;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepo repo;
	private BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);
	
	public List<Employee> getAll() {
		return repo.findAll();
	}
	
	
	public Employee addEmployee(Employee e) {
		e.setPassword(encoder.encode(e.getPassword()));
		return repo.save(e);
	}

}
