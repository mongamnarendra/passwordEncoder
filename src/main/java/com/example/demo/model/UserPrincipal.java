package com.example.demo.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{

	private Employee e;
	public UserPrincipal(Employee e) {
		this.e=e;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
				return Collections.singleton(new SimpleGrantedAuthority("User"));
	}

	@Override
	public String getPassword() {
		return e.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return e.getName();
	}

}
