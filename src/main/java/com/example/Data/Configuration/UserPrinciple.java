package com.example.Data.Configuration;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Data.Domain.User;

public class UserPrinciple implements UserDetails{
  
	private User user;
	public UserPrinciple(User user2) {
		this.user=user2;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.singletonList(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
	
			return user.getPassword();
	
		
	}

	@Override
	public String getUsername() {
		
		return user.getName();
	}
  
}
