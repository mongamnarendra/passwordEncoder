package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.model.UserPrincipal;
import com.example.demo.repo.EmployeeRepo;

@Service
public class MyUserServiceDetails implements UserDetailsService{
	
	@Autowired
	private EmployeeRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee emp = repo.findByName(username);
		
		if(emp==null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new UserPrincipal(emp);
		
	}

}
