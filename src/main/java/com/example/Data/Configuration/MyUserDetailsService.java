package com.example.Data.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Data.Domain.User;
import com.example.Data.Repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=repo.findByUserName(username);
		System.out.println(username);
		if(user==null) {
			System.out.println("User Not Found");
			throw new UsernameNotFoundException("user not found");
		}
		return new UserPrinciple(user);
	}
 
}
