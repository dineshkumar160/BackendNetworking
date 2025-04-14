package com.example.Data;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.Data.Domain.Post;
import com.example.Data.Domain.User;
import com.example.Data.Repository.PostRepository;
import com.example.Data.Repository.UserRepository;



@SpringBootApplication
public class DataApplication{
	
	@Autowired
	private PostRepository postRepo;
	
	
	@Autowired
	private UserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
	}



}
